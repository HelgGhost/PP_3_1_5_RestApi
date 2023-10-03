package ru.kata.spring.bootstrap.demo.init;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.bootstrap.demo.service.RoleService;
import ru.kata.spring.bootstrap.demo.service.UserService;
import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;

@Component
public class Init {
    private final Environment env;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(Environment env, PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        for (Role role : Role.getAllRoles()) {
            if (roleService.get(role.getName()) == null) {
                roleService.add(new Role(role.getName()));
            }
        }

        if (userService.get(env.getProperty("my.administrator.name")) == null) {
            User user = new User(env.getProperty("my.administrator.name"),
                    passwordEncoder.encode(env.getProperty("my.administrator.password")));
            user.addRole(roleService.get(Role.getRole(Role.ADMIN)));
            userService.add(user);
        }

        if (userService.get("user") == null) {
            User user = new User(env.getProperty("my.user.name"),
                    passwordEncoder.encode(env.getProperty("my.user.password")));
            user.addRole(roleService.get(Role.getRole(Role.USER)));
            userService.add(user);
        }
    }


}
