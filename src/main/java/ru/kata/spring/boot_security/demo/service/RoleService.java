package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {
    void add(Role role);

    Role get(String name);

    void delete(Role role);
}
