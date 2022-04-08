package com.project.rbd.service;

import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.exception.UnknownServiceTypeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class DBServiceFactoryTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void createDbService__shouldReturnQueryService() throws UnknownServiceTypeException {
        DBServiceFactory dbServiceFactory = new DBServiceFactory();
        DBService dbService;

        String testType = "query";
        DBConnectionData dbConnectionData = new DBConnectionData();

        dbService = dbServiceFactory.createDbService(testType, dbConnectionData);

        assertEquals("MSSQLQueryService", dbService.getClass().getSimpleName());
    }

    @Test
    public void createDbService__shouldReturnJdbcService() throws UnknownServiceTypeException {
        DBServiceFactory dbServiceFactory = new DBServiceFactory();
        DBService dbService;

        String testType = "jdbc";
        DBConnectionData dbConnectionData = new DBConnectionData();

        dbService = dbServiceFactory.createDbService(testType, dbConnectionData);

        assertEquals("MSSQLJDBCService", dbService.getClass().getSimpleName());
    }

    @Test
    public void createDbService__shouldThrowExceptionForUnknownType() throws UnknownServiceTypeException {
        DBServiceFactory dbServiceFactory = new DBServiceFactory();

        String testType = "test";
        DBConnectionData dbConnectionData = new DBConnectionData();

        exception.expect(UnknownServiceTypeException.class);

        dbServiceFactory.createDbService(testType, dbConnectionData);
    }
}