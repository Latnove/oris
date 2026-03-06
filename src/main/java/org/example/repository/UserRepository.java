package org.example.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserRepository {
    private SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
}
