package ru.kata.spring.boot_security.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Entity
@Table(name = "users")

public class User implements UserDetails {

    @Transient
    static UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String lastname;


    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToMany (cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user.id"),
            inverseJoinColumns = @JoinColumn(name = "role.id")
    )

    private Set<Role> roles = new HashSet<>();
    public User() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void addRole(Role role) {
        roles.add(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void deleteRole(Role role) {
        roles.remove(role);
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public User(String username, String password) {
        this.name = username;
        this.lastname = "";
        this.username = username;
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
