package com.project.rbd.services;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<String> retrieveTables() {
        ArrayList<String> tableNames = new ArrayList<>();

        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the tables in the database
            ResultSet tables = statement.executeQuery("select * from INFORMATION_SCHEMA.TABLES");

            while(tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                logger.info(tableName);
                tableNames.add(tableName);
            }
            logger.info("Retrieving tables COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return tableNames;
    }

    public void showColumns(String tableName) {
        logger.info("Retrieving columns for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the columns in the database
            ResultSet columns = statement.executeQuery("select * from INFORMATION_SCHEMA.COLUMNS where table_name = '" + tableName + "'");

            while(columns.next()) {
                logger.info(columns.getString("COLUMN_NAME"));
            }
            logger.info("Retrieving columns COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showConstraints(String tableName) {
        logger.info("Retrieving constraints for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the constraints in the database
            ResultSet constraints = statement.executeQuery("select * from sys.key_constraints where object_id = object_id('" + tableName + "')");

            while(constraints.next()) {
                logger.info(constraints.getString("name"));
            }

            constraints = statement.executeQuery("select * from sys.foreign_keys where object_id = object_id('" + tableName + "')");

            while(constraints.next()) {
                logger.info(constraints.getString("name"));
            }
            logger.info("Retrieving constraints COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showIndexInfo(String tableName) {
        logger.info("Retrieving indexes for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             Statement statement = connection.createStatement();
        ) {
            //Retrieving the indexes in the database
            ResultSet indexes = statement.executeQuery("select * from sys.indexes where object_id = object_id('" + tableName + "')");

            while(indexes.next()) {
                if (indexes.getString("name") != null) {
                    logger.info(indexes.getString("name"));
                }
            }
            logger.info("Retrieving indexes COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }
}
