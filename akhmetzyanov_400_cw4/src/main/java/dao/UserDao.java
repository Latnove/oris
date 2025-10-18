package dao;

import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAll();

    void save(User user);

    Optional<User> getByLogin(String login);
}
