package ru.kata.spring.bootstrap.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.bootstrap.demo.model.User;
import ru.kata.spring.bootstrap.demo.service.RoleService;
import ru.kata.spring.bootstrap.demo.service.UserService;
import ru.kata.spring.bootstrap.demo.util.UserValidator;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping()
    public String showAdminPage() {
       return "admin";
    }
}