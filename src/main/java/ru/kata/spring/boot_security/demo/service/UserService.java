package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(Long id);

    void add(User user);

    void update(Long id, User user);

    void delete(Long id);

}
