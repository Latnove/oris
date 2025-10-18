package org.example.service.impl;

import java.util.List;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(u -> new UserDto(u.getName(), u.getLogin())).toList();

    }

    @Override
    public User getByLogin(String login) {
        User user = userDao.getByLogin(login);
        
        return user;
    }
}
