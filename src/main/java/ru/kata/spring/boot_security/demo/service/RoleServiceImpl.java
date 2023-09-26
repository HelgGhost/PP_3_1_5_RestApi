package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleServiceDAO;
import ru.kata.spring.boot_security.demo.model.Role;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleServiceDAO roleServiceDAO;

    @Autowired
    public RoleServiceImpl(RoleServiceDAO roleServiceDAO) {
        this.roleServiceDAO = roleServiceDAO;
    }

    @Override
    public void add(Role role) {
        roleServiceDAO.add(role);
    }

    @Override
    public Role get(String name) {
        return roleServiceDAO.get(name);
    }

    @Override
    public void delete(Role role) {
        roleServiceDAO.delete(role.getName());
    }
}
