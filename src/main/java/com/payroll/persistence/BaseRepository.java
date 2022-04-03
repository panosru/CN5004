package com.payroll.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

public abstract class BaseRepository<T extends BaseEntity>
{
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");

    protected final EntityManager em = emf.createEntityManager();

    protected final CriteriaBuilder cb = em.getCriteriaBuilder();
    
    protected final EntityTransaction transaction = em.getTransaction();

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
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        Root<T> rootEntry = cq.from(persistentClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public T findById(final UUID id)
    {
        T entity = em.find(persistentClass, id);
        if (entity != null)
            em.detach(entity);

        return entity;
    }

    public T save(T entity)
    {
        try
        {
            transaction.begin();

            if (em.contains(entity) || entity.getId() != null)
            {
                entity = em.merge(entity);
            }
            else
            {
                em.persist(entity);
            }
            flushAndClear();

            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
        }

        return entity;
    }

    public void remove(T entity)
    {
        try
        {
            transaction.begin();

            em.remove(
                em.contains(entity)
                ? entity
                : em.getReference(entity.getClass(), entity.getId())
            );
            flushAndClear();
            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    protected void flushAndClear()
    {
        em.flush();
        em.clear();
    }
}
