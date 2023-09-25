package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserServiceDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
//public class UserServiceImpl implements UserService {
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserServiceDAO userServiceDAO;
    private final RoleService roleService;
    private final Environment env;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void runAfterStartup() {
        if (roleService.get(Role.getRole(Role.USER)) == null) {
            roleService.add(new Role(Role.getRole(Role.USER)));
        }
        if (roleService.get(Role.getRole(Role.ADMIN)) == null) {
            roleService.add(new Role(Role.getRole(Role.ADMIN)));
        }

        if (get(env.getProperty("administrator.name")) == null) {
            User user = new User(env.getProperty("administrator.name"),
                    passwordEncoder.encode(env.getProperty("administrator.password")));
            user.addRole(roleService.get(Role.getRole(Role.ADMIN)));
            add(user);
        }
    }

    @Autowired
    public UserServiceImpl(UserServiceDAO userServiceDAO, RoleService roleService, Environment env) {
        this.userServiceDAO = userServiceDAO;
        this.roleService = roleService;
        this.env = env;
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
        user.addRole(roleService.get(Role.getRole(Role.USER)));
        userServiceDAO.add(user);
    }

    @Override
    public void update(Long id, User user) {
        userServiceDAO.update(id, user);
    }

    @Override
    public void updateFromController(User user) {
        user.setRoles(get(user.getId()).getRoles());
        userServiceDAO.update(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        userServiceDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = get(username);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
