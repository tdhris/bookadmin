package com.sap.internship.libraryadmin.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class EntityManagerHelper {
    private static EntityManager em;
    private final static String persistenceUnit = "library-admin-jpa";

    public static EntityManager getEntityManager(DataSource dataSource) {
        if (em == null) {
            em = createEntityManagerFactory(dataSource).createEntityManager();
        }
        return em;
    }

    private static EntityManagerFactory createEntityManagerFactory(DataSource dataSource) {
        final Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);
        return Persistence.createEntityManagerFactory(persistenceUnit, properties);
    }
}
