package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserServiceDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
//public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserServiceDAO userServiceDAO;

    @Autowired
    public UserServiceImpl(UserServiceDAO userServiceDAO) {
        this.userServiceDAO = userServiceDAO;
    }

    @Override
    public List<User> getAll() {
        return userServiceDAO.getAll();
    }

    @Override
    public List<Role> getRoles(User user) {
        return null;
    }

    @Override
    public User get(Long id) {
        return userServiceDAO.get(id);
    }

    @Override
    public User get(String email) {
        return userServiceDAO.get(email);
    }

    @Override
    public void add(User user) {
        userServiceDAO.add(user);
    }

    @Override
    public void update(Long id, User user) {
        userServiceDAO.update(id, user);
    }

    @Override
    public void delete(Long id) {
        userServiceDAO.delete(id);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = get(username);
//        if (user==null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return user;
//    }
}
