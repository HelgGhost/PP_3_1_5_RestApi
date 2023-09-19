package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUsersPage(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.get(id));
        return "user";
    }

    @GetMapping("/new")
    public String showNewPage(ModelMap model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String addUserRedirectUsers(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.get(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUserRedirectUsers(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUserRedirectUsers(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}