package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.rest.JpaConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaUserRepository implements UserRepository
{
    private static EntityManagerFactory factory = JpaConnection.create();

    public JpaUserRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public User create(String username, String password, String email) {
        EntityManager entityManager=  factory.createEntityManager();
        User user = new User(username, password, email);
        entityManager.persist(user);
        return user;
    }


    @Override
    public User delete(User user) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.remove(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.createNamedQuery("User.findById", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public User getByEmail(String email) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }


}
