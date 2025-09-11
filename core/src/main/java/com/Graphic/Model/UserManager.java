package com.Graphic.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.badlogic.gdx.graphics.Texture;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private static final String FILE_PATH = "users.json";
    private static HashMap<String, User> users = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        loadUsers();
    }

    public static void saveUser(User user) {
        users.put(user.getName(), user);
        saveUsers();
    }
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }
    public static User loadUser(String username) {
        User user = users.get(username);
        if (user != null && user.getAvatarPath() != null) {
            user.setAvatar(new Texture(user.getAvatarPath()));
        }
        return user;
    }
    private static void saveUsers() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteUser(String username) {
        if (users.remove(username) != null) {
            saveUsers();
        }
    }
    public static Collection<User> getAllUsers() {
        return users.values();
    }
    private static void loadUsers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<HashMap<String, User>>() {}.getType();
            users = gson.fromJson(reader, type);
            if (users == null) users = new HashMap<>();
        } catch (IOException e) {
            users = new HashMap<>();
        }
    }
}
