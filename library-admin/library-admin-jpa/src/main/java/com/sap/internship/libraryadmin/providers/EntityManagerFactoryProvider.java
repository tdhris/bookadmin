package com.sap.internship.libraryadmin.providers;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.sap.internship.libraryadmin.providers.DataSourceProvider;

public class EntityManagerFactoryProvider {
    private static EntityManagerFactoryProvider instance = null;
    private static EntityManagerFactory factory = null;
    private final static String PERSISTENCE_UNIT = "library-admin-jpa";

    // make constructor private
    private EntityManagerFactoryProvider() {
    }

    // only one instance of the provider for the application
    public static synchronized EntityManagerFactoryProvider getInstance() {
        if (instance == null) {
            instance = new EntityManagerFactoryProvider();
        }
        return instance;
    }

    public synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            DataSource source = DataSourceProvider.getInstance().get();
            factory = createNewEntityManagerFactory(source);
        }
        return factory;
    }

    private EntityManagerFactory createNewEntityManagerFactory(DataSource dataSource) {
        final Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
    }

    public synchronized void close() {
        if (factory != null) {
            factory.close();
            factory = null;
        }
    }
}
