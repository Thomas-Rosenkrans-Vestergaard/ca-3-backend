package com.group3.ca3.repositories;

import com.group3.ca3.data.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaUserRepository implements UserRepository
{
    private static EntityManagerFactory factory;


    @Override
    public User create(String username, String password, String email) {
        EntityManager entityManager=  factory.createEntityManager();
        User user = new User(username, password, email);
        entityManager.persist(user);
        return user;
    }




    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User getById(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.createNamedQuery("User.findById", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }


}
