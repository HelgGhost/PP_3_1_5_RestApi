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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppRestApiController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AppRestApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
    @PostMapping()
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user, BindingResult bindingResult) {
        userService.addFromController(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
