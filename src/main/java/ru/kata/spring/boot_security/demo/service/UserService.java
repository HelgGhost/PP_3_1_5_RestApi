package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAll();
    User get(Long id);
    User get(String username);

    void add(User user);

    void update(Long id, User user);
    void updateFromController(User user);

    void delete(Long id);

}
