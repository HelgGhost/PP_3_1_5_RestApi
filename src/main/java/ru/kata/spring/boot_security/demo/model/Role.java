package ru.kata.spring.boot_security.demo.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column
    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

}
