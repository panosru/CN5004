package com.payroll.service;

import com.payroll.App;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;

public final class ORMService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile ORMService instance;

    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    private ORMService()
    {
        setupSessionFactory();
    }

    /**
     * Thread-safe singleton implementation
     * @return the instance
     */
    public static ORMService getInstance()
    {
        ORMService localInstance = instance;
        // Thread-safe singleton
        if (null == localInstance)
            synchronized (ORMService.class)
            {
                localInstance = instance;
                if (null == instance)
                    instance = localInstance = new ORMService();
            }

        return localInstance;
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void shutdown()
    {
        if (registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }

    private void setupSessionFactory()
    {
        if (sessionFactory == null)
        {
            try
            {
                // Create registry
                registry = new StandardServiceRegistryBuilder()
                    .configure(App.getResource(StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME))
                    .build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            }
            catch (Exception e)
            {
                e.printStackTrace();

                if (registry != null)
                    StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }
}
