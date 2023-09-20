package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UsersController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
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
    public String addUserRedirectUsers(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.get(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUserRedirectUsers(@ModelAttribute("user") User user, BindingResult bindingResult , @PathVariable("id") Long id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUserRedirectUsers(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}