package ru.kata.spring.bootstrap.demo.dao;

import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;

public interface RoleServiceDAO {
    void add(Role role);

    Role get(String name);

    List<Role> getAll();

    void delete(String name);

}
