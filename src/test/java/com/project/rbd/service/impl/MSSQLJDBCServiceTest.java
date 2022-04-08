package com.project.rbd.service.impl;

import com.project.rbd.dto.db.DBConnectionData;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MSSQLJDBCServiceTest {
    private final String errorMessage = "Something went wrong. ";
    private final String tableName = "test";
    private ByteArrayOutputStream outContent;
    private MSSQLJDBCService mssqljdbcService;

    @Before
    public void init() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DBConnectionData dbConnectionData = new DBConnectionData();
        mssqljdbcService = new MSSQLJDBCService(dbConnectionData);
    }

    @Test
    public void getTables() {
        mssqljdbcService.getTables();

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getColumns() {
        mssqljdbcService.getColumns(tableName);

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getConstraints() {
        mssqljdbcService.getConstraints(tableName);

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getIndexes() {
        mssqljdbcService.getIndexes(tableName);

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getFunctions() {
        mssqljdbcService.getFunctions();

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getProcedures() {
        mssqljdbcService.getProcedures();

        assertTrue(outContent.toString().contains(errorMessage));
    }

    @Test
    public void getTriggers() {
        mssqljdbcService.getTriggers();

        assertTrue(outContent.toString().contains(errorMessage));
    }
}