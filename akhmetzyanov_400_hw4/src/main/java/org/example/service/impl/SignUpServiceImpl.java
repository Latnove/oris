package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.entity.User;
import org.example.service.SignUpService;
import org.example.util.PasswordUtil;

public class SignUpServiceImpl implements SignUpService {
    private UserDao userDao = new UserDaoImpl();

    private User getUser(String login) {
        User user = userDao.getByLogin(login);
        if (user != null) {
            return user;
        }

        return null;
    }

    @Override
    public Boolean saveUser(User user) {
        if (getUser(user.getLogin()) != null) {
            return false;
        }
        userDao.save(new User(user.getId(), user.getName(), user.getLastname(), user.getLogin(), PasswordUtil.encrypt(user.getPassword())));

        return true;
    }
}
