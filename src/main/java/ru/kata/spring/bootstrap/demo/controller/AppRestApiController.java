package ru.kata.spring.bootstrap.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;
import ru.kata.spring.bootstrap.demo.service.RoleService;
import ru.kata.spring.bootstrap.demo.service.UserService;
import ru.kata.spring.bootstrap.demo.util.UserValidator;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppRestApiController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AppRestApiController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @GetMapping("/user")
    public User getUser(Principal principal) {
        return userService.get(principal.getName());
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.CONFLICT);
        }
        userService.addFromController(user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") Long id,
                                        BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.CONFLICT);
        }
        userService.updateFromController(id, user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}

