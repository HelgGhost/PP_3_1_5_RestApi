package ru.kata.spring.bootstrap.demo.service;

import ru.kata.spring.bootstrap.demo.model.Role;

public interface RoleService {
    void add(Role role);

    Role get(String name);

    void delete(Role role);
}
