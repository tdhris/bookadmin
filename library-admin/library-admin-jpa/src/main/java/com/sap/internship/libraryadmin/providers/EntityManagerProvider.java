package com.sap.internship.libraryadmin.providers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerProvider {
    private static final ThreadLocal<EntityManager> ENTITY_MANAGER = new ThreadLocal<EntityManager>();
    private static EntityManagerProvider instance = null;

    // make default constructor private
    private EntityManagerProvider() {
    }

    public static synchronized EntityManagerProvider getInstance() {
        if (instance == null) {
            instance = new EntityManagerProvider();
        }
        return instance;
    }

    public void initialize() {
        EntityManagerFactory factory = EntityManagerFactoryProvider.getInstance().getEntityManagerFactory();
        EntityManager manager = factory.createEntityManager();
        set(manager);
    }

    public EntityManager get() {
        return ENTITY_MANAGER.get();
    }

    public void closeEntityManager() {
        EntityManager manager = get();
        if (manager != null) {
            manager.close();
        }
        remove();
    }

    private static void set(EntityManager manager) {
        ENTITY_MANAGER.set(manager);
    }

    private static void remove() {
        ENTITY_MANAGER.remove();
    }

}
