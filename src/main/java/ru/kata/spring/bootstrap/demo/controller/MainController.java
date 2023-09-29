package ru.kata.spring.bootstrap.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String mainPageRedirectUsers() {
        //model.addAttribute("principal", principal);
        return "redirect:login";
    }

}