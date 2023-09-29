package ru.kata.spring.bootstrap.demo.dao;

import ru.kata.spring.bootstrap.demo.model.Role;

public interface RoleServiceDAO {
    void add(Role role);

    Role get(String name);

    void delete(String name);

}
