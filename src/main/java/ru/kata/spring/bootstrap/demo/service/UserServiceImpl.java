package ru.kata.spring.bootstrap.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.bootstrap.demo.dao.UserServiceDAO;
import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//public class UserServiceImpl implements UserService {
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserServiceDAO userServiceDAO;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserServiceDAO userServiceDAO, RoleService roleService, Environment env, PasswordEncoder passwordEncoder) {
        this.userServiceDAO = userServiceDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userServiceDAO.getAll();
    }

    @Override
    public User get(Long id) {
        return userServiceDAO.get(id);
    }

    @Override
    public User get(String username) {
        return userServiceDAO.get(username);
    }

    @Override
    public void add(User user) {
        //user.addRole(roleService.get(Role.getRole(Role.USER)));
        userServiceDAO.add(user);
    }

    @Override
    public void update(Long id, User user) {
        userServiceDAO.update(id, user);
    }

    @Override
    public void updateFromController(Long id, User user) {
        linkRoles(user);
        if (user.getPassword().equals("")) {
            user.setPassword(get(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        update(id, user);
    }

    @Override
    public void addFromController(User user) {
        linkRoles(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        add(user);
    }

    @Override
    public void delete(Long id) {
        userServiceDAO.delete(id);
    }

    @Override
    public void linkRoles(User user) {
        user.setRoles(user.getRoles().stream().map(r -> roleService.get(r.getName())).collect(Collectors.toSet()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = get(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
