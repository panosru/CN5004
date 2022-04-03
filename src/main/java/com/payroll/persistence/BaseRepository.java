package com.payroll.persistence;

import com.payroll.service.ORMService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

public abstract class BaseRepository<T extends BaseEntity>
{
    protected final SessionFactory sessionFactory = ORMService.getInstance().getSessionFactory();

    protected final Session session = sessionFactory.openSession();

    protected Transaction transaction = session.getTransaction();

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
        Query query = session.createQuery("SELECT obj FROM " + persistentClass.getSimpleName() + " obj");
        return query.list();
    }

    public T findById(final UUID id)
    {
        T entity = session.find(persistentClass, id);
        if (entity != null)
            session.detach(entity);

        return entity;
    }

    public T save(T entity)
    {
        try
        {
            transaction.begin();

            if (session.contains(entity) || entity.getId() != null)
            {
                entity = (T) session.merge(entity);
            }
            else
            {
                session.persist(entity);
            }
            flushAndClear();

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null)
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

            session.remove(
                session.contains(entity)
                ? entity
                : session.getReference(entity.getClass(), entity.getId())
            );
            flushAndClear();
            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    protected void flushAndClear()
    {
        session.flush();
        session.clear();
    }
}
