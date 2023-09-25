package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String showAdminPage(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable("id") Long id, Principal principal, ModelMap model) {
        model.addAttribute("user", userService.get(id));
        return "admin/user";
    }

    @GetMapping("/new")
    public String showNewPage(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping()
    public String addUserRedirectUsers(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.get(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUserRedirectUsers(@ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/edit";
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
