package org.example.service;

import java.util.List;

import org.example.dto.UserDto;
import org.example.entity.User;

public interface UserService {
    List<UserDto> getAll();
    User getByLogin(String login);
}
