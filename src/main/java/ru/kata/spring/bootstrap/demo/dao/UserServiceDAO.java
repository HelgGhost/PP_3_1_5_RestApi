package ru.kata.spring.bootstrap.demo.dao;


import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;

public interface UserServiceDAO {
    List<User> getAll();

    User get(Long id);

    User get(String name);

    void add(User user);

    void update(Long id, User user);

    void delete(Long id);
}
