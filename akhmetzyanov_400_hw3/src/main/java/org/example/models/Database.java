package org.example.models;

import java.util.HashMap;
import java.util.Map;

public class Database {
    public static Map<String, User> users = new HashMap<>();

    public static User getUser(String login) {
        if (users.containsKey(login.toLowerCase())) {
            return users.get(login);
        } else {
            return null;
        }
    }

    public static void addUser(String login, String password, String name) {
        if (users.containsKey(login.toLowerCase())) {
            return;
        } else {
            users.put(login.toLowerCase(), new User( login.toLowerCase(), password, name));
        }
    }
}
