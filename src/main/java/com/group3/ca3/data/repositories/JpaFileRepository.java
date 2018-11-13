package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;

public class JpaFileRepository implements FileRepository
{

    private final EntityManagerFactory entityManagerFactory;

    public JpaFileRepository(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override public File create(String title, int size, String mime, String extension, String fileIOKey, LocalDateTime expiry)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            File file = new File(title, size, mime, extension, fileIOKey, expiry);
            entityManager.persist(file);
            entityManager.getTransaction().commit();
            return file;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override public List<File> get()
    {
        return entityManagerFactory.createEntityManager()
                                   .createQuery("from File", File.class)
                                   .getResultList();
    }

    @Override public File get(long id)
    {
        try {
            return entityManagerFactory.createEntityManager()
                                       .createQuery("SELECT f from File f WHERE f.id = :id", File.class)
                                       .setParameter("id", id)
                                       .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override public List<File> search(String title)
    {
        return entityManagerFactory
                .createEntityManager()
                .createQuery("SELECT f FROM File f ORDER BY LEVENSHTEIN(:search, f.title) ASC", File.class)
                .setParameter("search", title)
                .getResultList();
    }

    @Override public List<File> getByUser(long user)
    {
        throw new UnsupportedOperationException();
    }

    @Override public List<File> searchByUser(String title, long user)
    {
        throw new UnsupportedOperationException();
    }
}
