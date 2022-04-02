package com.payroll.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

public abstract class BaseRepository<T extends BaseEntity>
{
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");

    protected final EntityManager entityManager = entityManagerFactory.createEntityManager();

    protected final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    protected final Class<T> persistentClass;

    protected BaseRepository()
    {
        // Using reflection to get the class of the given type via reflection.
        // Will not work with dynamic proxies.
        this.persistentClass = (Class<T>)
            ((ParameterizedType)getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public List<T> findAll()
    {
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(persistentClass);
        Root<T> rootEntry = cq.from(persistentClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public T findById(final UUID id)
    {
        T entity = entityManager.find(persistentClass, id);
        if (entity != null)
            entityManager.detach(entity);

        return entity;
    }

    public T save(T entity)
    {
        try
        {
            entityManager.getTransaction().begin();

            if (entityManager.contains(entity) || entity.getId() != null)
            {
                entity = entityManager.merge(entity);
            }
            else
            {
                entityManager.persist(entity);
            }

            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return entity;
    }

    public void remove(T entity)
    {
        try
        {
            entityManager.getTransaction().begin();
            if (entityManager.contains(entity))
                entityManager.remove(entity);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
