package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAll();
    List<Role> getRoles(User user);

    User get(Long id);
    User get(String email);

    void add(User user);

    void update(Long id, User user);

    void delete(Long id);

}
