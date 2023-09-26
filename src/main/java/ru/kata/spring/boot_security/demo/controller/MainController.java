package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String mainPageRedirectUsers(Principal principal, ModelMap model) {
        model.addAttribute("principal", principal);
        return "index";
    }

}