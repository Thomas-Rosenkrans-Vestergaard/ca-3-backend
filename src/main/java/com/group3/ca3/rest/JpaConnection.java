package com.group3.ca3.rest;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.jwt.Role;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaConnection
{
    private static EntityManagerFactory emf;

    public static EntityManagerFactory create()
    {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("rest-api-pu");

        return emf;
    }

    public static void main(String[] args)
    {
        User user = new User("user name", BCrypt.hashpw("1234", BCrypt.gensalt()), "user" +
                "@email.com", Role.USER);
        User admin = new User("admin name", BCrypt.hashpw("1234", BCrypt.gensalt()), "admin@email.com", Role.ADMIN);
        EntityManager entityManager = create().createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.persist(admin);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}