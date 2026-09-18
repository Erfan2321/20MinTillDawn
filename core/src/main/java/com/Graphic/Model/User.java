package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {

    public static final String DEFAULT_AVATAR = "avatar1.png";

    private static final String[] STARTING_AVATARS = {"avatar1.png", "avatar2.png"};
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final SecureRandom RANDOM = new SecureRandom();

    /** At least 8 characters, with an upper case letter, a lower case letter, a digit and a symbol. */
    private static final String STRONG_PASSWORD = "^(?=.*[@#$%&*)(_])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";

    private String name;
    /**
     * Salted SHA-256 of the password, as lowercase hex. The plain password is never stored.
     */
    private String passHash;
    private String passSalt;
    /**
     * Only ever set by save files written before password hashing existed. It is migrated to
     * {@link #passHash} the first time the password is checked, and dropped from the save file.
     */
    private String pass;
    private String secQuestion;

    private int point;
    private int mustKill;
    private int mustTime;
    private String avatarPath;
    private transient Texture avatar;

    public User(String name, String password, String secQuestion) {

        this.point = 0;
        this.mustTime = 0;
        this.mustKill = 0;
        this.name = name;
        this.secQuestion = secQuestion;
        this.avatarPath = STARTING_AVATARS[RANDOM.nextInt(STARTING_AVATARS.length)];

        setPassword(password);
    }

    public static boolean isStrongPassword(String password) {

        return password != null && password.matches(STRONG_PASSWORD);
    }

    /**
     * Replaces the stored password with a fresh salt and hash.
     */
    public void setPassword(String password) {

        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        this.passSalt = toHex(salt);
        this.passHash = hash(password, this.passSalt);
        this.pass = null;
    }

    /**
     * @return true when {@code password} matches the stored one. Accounts saved before hashing
     * was introduced are upgraded in place on the first successful check.
     */
    public boolean checkPassword(String password) {

        if (pass != null) {
            boolean matches = pass.equals(password);
            if (matches)
                setPassword(password);
            return matches;
        }
        if (passHash == null || passSalt == null)
            return false;

        return constantTimeEquals(passHash, hash(password, passSalt));
    }

    private static String hash(String password, String salt) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes(UTF8));
            return toHex(digest.digest(password.getBytes(UTF8)));
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is required on every Java platform, so this cannot happen in practice.
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    private static boolean constantTimeEquals(String a, String b) {

        if (a.length() != b.length())
            return false;

        int diff = 0;
        for (int i = 0; i < a.length(); i++)
            diff |= a.charAt(i) ^ b.charAt(i);

        return diff == 0;
    }

    private static String toHex(byte[] bytes) {

        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes)
            hex.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit(b & 0xF, 16));

        return hex.toString();
    }

    public int getPoint() {

        return this.point;
    }
    public String getName() {

        return name;
    }

    /**
     * Loads the avatar on first use so that a User can be created without a live GL context,
     * and so repeatedly reading a user from disk does not leak one texture per read.
     */
    public Texture getAvatar() {

        if (avatar == null)
            avatar = new Texture(resolveAvatar());

        return avatar;
    }

    private FileHandle resolveAvatar() {

        if (avatarPath != null && !avatarPath.isEmpty()) {
            FileHandle internal = Gdx.files.internal(avatarPath);
            if (internal.exists())
                return internal;

            // Avatars picked from a dropped file are stored as an absolute path.
            FileHandle absolute = Gdx.files.absolute(avatarPath);
            if (absolute.exists())
                return absolute;

            Gdx.app.error("User", "Avatar not found, falling back to default: " + avatarPath);
            avatarPath = DEFAULT_AVATAR;
        }
        return Gdx.files.internal(DEFAULT_AVATAR);
    }

    public String getSecQuestion() {

        return secQuestion;
    }

    public void setName(String name) {

        this.name = name;
    }
    public void setPoint(int point) {

        this.point = point;
    }
    public void setAvatar(Texture avatar) {

        this.avatar = avatar;
    }
    public void setAvatarPath(String avatarPath) {

        if (avatarPath != null && !avatarPath.equals(this.avatarPath))
            this.avatar = null;

        this.avatarPath = avatarPath;
    }
    public void setSecQuestion(String secQuestion) {

        this.secQuestion = secQuestion;
    }

    public String getAvatarPath() {
        return this.avatarPath;
    }
    public void setMustKill(int i) {
        this.mustKill = i;
    }
    public void setMustTime(int i) {
        this.mustTime = i;
    }
    public int getMustKill() {
        return this.mustKill;
    }
    public int getMustTime() {
        return mustTime;
    }
}
