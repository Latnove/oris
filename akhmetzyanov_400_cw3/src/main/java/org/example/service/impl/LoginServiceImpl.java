package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.entity.User;
import org.example.service.LoginService;
import org.example.util.PasswordUtil;

public class LoginServiceImpl implements LoginService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User getUser(String login, String password) {
        User user = userDao.getByLogin(login);
        if (user != null) {
            if (user.getPassword().equals(PasswordUtil.encrypt(password))) {
                return user;
            }
        }

        return null;
    }
}
