package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class User {

    private String name;
    private String pass;
    private String secQuestion;

    private int point;
    private int mustKill;
    private int mustTime;
    private String avatarPath;
    private transient Texture avatar;


    public User (String name, String pass, String secQuestion) {

        this.point = 0;
        this.mustTime = 0;
        this.mustKill = 0;
        this.name = name;
        this.pass = pass;
        this.secQuestion = secQuestion;
        double i = Math.random();

        if (i < 0.33)
            avatarPath = "back1.png";
        else if (i < 0.66)
            avatarPath = "avatar1.png";
        else
            avatarPath = "avatar2.png";

        avatar = new Texture(Gdx.files.internal(avatarPath));
    }

    public int getPoint() {

        return this.point;
    }
    public String getName() {

        return name;
    }
    public String getPass() {

        return pass;
    }
    public Texture getAvatar() {

        return avatar;
    }
    public String getSecQuestion() {

        return secQuestion;
    }

    public void setPass(String pass) {

        this.pass = pass;
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
