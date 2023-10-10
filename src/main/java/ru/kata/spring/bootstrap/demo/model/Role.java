package ru.kata.spring.bootstrap.demo.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String MODERATOR = "MODERATOR";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public static String getRole(String role) {
        return "ROLE_" + role;
    }

    public static List<Role> getAllRoles() {
        List<Role> allRoles = new ArrayList<>();
        allRoles.add(new Role(Role.getRole(ADMIN)));
        allRoles.add(new Role(Role.getRole(USER)));
        allRoles.add(new Role(Role.getRole(MODERATOR)));
        return allRoles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
