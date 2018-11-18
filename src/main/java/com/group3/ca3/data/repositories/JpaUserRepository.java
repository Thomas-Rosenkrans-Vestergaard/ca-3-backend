package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.jwt.Role;
import com.group3.ca3.rest.JpaConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class JpaUserRepository implements UserRepository
{
    private static EntityManagerFactory factory = JpaConnection.create();

    public JpaUserRepository(EntityManagerFactory factory)
    {
        this.factory = factory;
    }

    @Override
    public User create(String username, String password, String email, Role role)
    {
        EntityManager entityManager = factory.createEntityManager();
        User          user          = new User(username, password, email, role);
        entityManager.persist(user);
        return user;
    }


    @Override
    public User delete(User user)
    {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.remove(user);
        return user;
    }

    @Override
    public User getById(Long id)
    {
        try {
            EntityManager entityManager = factory.createEntityManager();
            return entityManager.createNamedQuery("User.findById", User.class)
                                .setParameter("id", id)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email)
    {
        try {
            EntityManager entityManager = factory.createEntityManager();
            return entityManager.createNamedQuery("User.findByEmail", User.class)
                                .setParameter("email", email)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
