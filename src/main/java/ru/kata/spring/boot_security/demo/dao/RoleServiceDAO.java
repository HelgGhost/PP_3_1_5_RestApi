package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

public interface RoleServiceDAO {
    void add(Role role);

    Role get(String name);

    void delete(Long id);

}
