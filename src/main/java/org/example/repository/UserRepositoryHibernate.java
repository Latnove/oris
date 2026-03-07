package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
public class UserRepositoryHibernate {
    private final SessionFactory sessionFactory;

    public UserRepositoryHibernate(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }

    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
}
