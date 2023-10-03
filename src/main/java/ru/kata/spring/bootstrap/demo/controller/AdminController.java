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
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String showAdminPage(Principal principal, ModelMap model) {

        model.addAttribute("users", userService.getAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("localUser", userService.get(principal.getName()));
        model.addAttribute("allRoles", roleService.getAll());
        return "admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.addFromController(user);
        return "redirect:/admin";
    }

    @PatchMapping()
    public String updateUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.updateFromController(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUserRedirectUsers(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
