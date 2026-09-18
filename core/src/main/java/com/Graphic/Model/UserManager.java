package com.Graphic.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {

    private static final String FILE_NAME = "users.json";
    private static final Type USER_MAP = new TypeToken<HashMap<String, User>>() {}.getType();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static HashMap<String, User> users;

    private UserManager() {
    }

    private static FileHandle file() {
        return GameFiles.data(FILE_NAME);
    }

    private static HashMap<String, User> users() {

        if (users == null)
            loadUsers();

        return users;
    }

    public static void saveUser(User user) {
        users().put(user.getName(), user);
        saveUsers();
    }
    public static boolean userExists(String username) {
        return users().containsKey(username);
    }
    public static User loadUser(String username) {
        return users().get(username);
    }
    public static void deleteUser(String username) {
        if (users().remove(username) != null)
            saveUsers();
    }
    public static Collection<User> getAllUsers() {
        return users().values();
    }

    private static void saveUsers() {
        try {
            file().writeString(gson.toJson(users, USER_MAP), false, "UTF-8");
        } catch (RuntimeException e) {
            Gdx.app.error("UserManager", "Could not save " + FILE_NAME, e);
        }
    }

    private static void loadUsers() {

        users = new HashMap<>();

        FileHandle handle = file();
        if (!handle.exists())
            return;

        try {
            HashMap<String, User> loaded = gson.fromJson(handle.readString("UTF-8"), USER_MAP);
            if (loaded != null)
                users = loaded;
        } catch (RuntimeException e) {
            // A corrupt file should not stop the game from starting; start from an empty roster.
            Gdx.app.error("UserManager", "Could not read " + FILE_NAME + ", starting empty", e);
        }
    }
}
