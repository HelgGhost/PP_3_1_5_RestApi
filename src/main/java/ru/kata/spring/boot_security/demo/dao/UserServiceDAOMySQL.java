package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class UserServiceDAOMySQL implements UserServiceDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public Set<Role> getRoles(User user) {
        Set<Role> =  entityManager.createQuery("SELECT r FROM Role r JOIN r.users users JOIN users.roles role WHERE r")
                .setParameter("email", user.getEmail()).getResultStream().findAny().orElse(null);
        return null;
    }

    @Override
    public User get(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User get(String email) {
        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email).getResultStream().findAny().orElse(null);
    }

    @Transactional
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.remove(get(id));
    }
}
