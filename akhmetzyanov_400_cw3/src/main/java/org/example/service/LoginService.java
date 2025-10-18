package org.example.service;

import org.example.entity.User;

public interface LoginService {
    User getUser(String login, String password);
}
