package com.sap.internship.libraryadmin.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceProvider {
    private static DataSource dataSource = null;
    private static DataSourceProvider instance = null;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // only one DataSourceProvider for the application
    public synchronized static DataSourceProvider getInstance() {
        if (instance == null) {
            instance = new DataSourceProvider();
        }
        return instance;
    }

    private DataSourceProvider() {
        try {
            final InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
        } catch (NamingException e) {
            logger.error("Could not lookup the default datasource", e);
            throw new RuntimeException(e);
        }
    }

    public DataSource get() {
        return dataSource;
    }

}
