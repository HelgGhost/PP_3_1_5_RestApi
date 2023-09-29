package ru.kata.spring.bootstrap.demo.service;


import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(Long id);

    User get(String username);

    void add(User user);

    void update(Long id, User user);

    void updateFromController(User user);

    void delete(Long id);

}
