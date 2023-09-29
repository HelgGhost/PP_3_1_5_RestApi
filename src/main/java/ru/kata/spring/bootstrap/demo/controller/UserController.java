package ru.kata.spring.bootstrap.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.bootstrap.demo.service.UserService;
import ru.kata.spring.bootstrap.demo.util.UserValidator;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/user")
    public String showUserPage(Principal principal, ModelMap model) {
        model.addAttribute("user", userService.get(principal.getName()));
        return "user";
    }

}