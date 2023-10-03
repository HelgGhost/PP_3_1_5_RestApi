package ru.kata.spring.bootstrap.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.bootstrap.demo.dao.RoleServiceDAO;
import ru.kata.spring.bootstrap.demo.model.Role;

import java.util.List;

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
    public List<Role> getAll() {
        return roleServiceDAO.getAll();
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
