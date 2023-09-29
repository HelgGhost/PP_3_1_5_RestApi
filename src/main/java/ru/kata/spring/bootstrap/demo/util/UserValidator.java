package ru.kata.spring.bootstrap.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.bootstrap.demo.model.User;
import ru.kata.spring.bootstrap.demo.service.UserService;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.get(user.getUsername()) != null && !user.equals(userService.get(user.getUsername()))) {
            errors.rejectValue("username", "", "Username already in use");
        }
        if (user.getPassword() == null) {
            errors.rejectValue("password", "", "Password must be not empty");
        }
    }
}
