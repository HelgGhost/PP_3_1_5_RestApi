package ru.kata.spring.bootstrap.demo.service;

import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;

public interface RoleService {
    void add(Role role);

    List<Role> getAll();

    Role get(String name);

    void delete(Role role);
}
