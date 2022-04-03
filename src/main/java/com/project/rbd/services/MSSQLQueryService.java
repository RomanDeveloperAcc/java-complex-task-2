package com.project.rbd.services;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLQueryService implements DBService {
    private final String jdbcUrl;
    private final String dbName;
    private final String dbLogin;
    private final String dbPassword;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLQueryService(DBConnectionData dbConnectionData) {
        this.jdbcUrl = dbConnectionData.jdbcUrl;
        this.dbName = dbConnectionData.dbName;
        this.dbLogin = dbConnectionData.dbLogin;
        this.dbPassword = dbConnectionData.dbPassword;
    }

    public void showTables() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the columns in the database
            ResultSet tables = statement.executeQuery("select * from INFORMATION_SCHEMA.TABLES");

            while(tables.next()) {
                System.out.print(tables.getString("TABLE_NAME"));
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showColumns() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the columns in the database
            ResultSet tables = statement.executeQuery("select * from INFORMATION_SCHEMA.COLUMNS");

            while(tables.next()) {
                System.out.print(tables.getString("COLUMN_NAME"));
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showConstraints() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the columns in the database
            ResultSet tables = statement.executeQuery("select * from INFORMATION_SCHEMA.TABLE_CONSTRAINTS");

            while(tables.next()) {
                System.out.print(tables.getString("COLUMN_NAME"));
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showIndexInfo() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the columns in the database
            ResultSet tables = statement.executeQuery("select * from INFORMATION_SCHEMA.COLUMNS");

            while(tables.next()) {
                System.out.print(tables.getString("COLUMN_NAME"));
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }
}
