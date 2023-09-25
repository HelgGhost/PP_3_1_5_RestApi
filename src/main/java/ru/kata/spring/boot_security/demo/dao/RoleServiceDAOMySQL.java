package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RoleServiceDAOMySQL implements RoleServiceDAO{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role get(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name).getResultStream().findAny().orElse(null);
    }

    @Override
    public void delete(Long id) {

    }
}
