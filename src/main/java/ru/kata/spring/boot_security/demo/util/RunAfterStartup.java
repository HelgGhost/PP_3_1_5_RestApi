package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.*;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
@Transactional
public class RunAfterStartup {
    private final Environment env;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RunAfterStartup(Environment env, PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        if (roleService.get(env.getProperty("role.user")) == null) {
            roleService.add(new Role(env.getProperty("role.user")));
        }
        if (roleService.get(env.getProperty("role.administrator")) == null) {
            roleService.add(new Role(env.getProperty("role.administrator")));
        }

        if (userService.get(env.getProperty("administrator.name")) == null) {
            User user = new User(env.getProperty("administrator.name"),
                    passwordEncoder.encode(env.getProperty("administrator.password")));
            user.addRole(roleService.get(env.getProperty("role.administrator")));
            userService.add(user);
        }
    }

    @PostConstruct
    public void prepareDatabase() {
//        //entityManager.createQuery("");
    }

}