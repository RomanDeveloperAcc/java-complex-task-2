package com.project.rbd.services;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLJDBCService implements DBService {
    private final String jdbcUrl;
    private final String dbName;
    private final String dbLogin;
    private final String dbPassword;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLJDBCService(DBConnectionData dbConnectionData) {
        this.jdbcUrl = dbConnectionData.jdbcUrl;
        this.dbName = dbConnectionData.dbName;
        this.dbLogin = dbConnectionData.dbLogin;
        this.dbPassword = dbConnectionData.dbPassword;
    }

    public void showTables() {
        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};

            //Retrieving the tables in the database
            ResultSet tables = metaData.getTables(null, null, null, types);

            while(tables.next()) {
                logger.info("table : " + tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showColumns() {
        logger.info("Retrieving columns...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the columns in the database
            ResultSet columns = metaData.getColumns(null, null, "test", null);

            while(columns.next()) {
                logger.info("column : " + columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showConstraints() {
        logger.info("Retrieving constraints...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            logger.info("Retrieving primary keys...");
            //Retrieving the columns in the database
            ResultSet columns = metaData.getPrimaryKeys(null, null, "test");

            while(columns.next()) {
                logger.info("primary key : " + columns.getString("PK_NAME"));
            }

            logger.info("Retrieving foreign keys...");
            //Retrieving the columns in the database
            columns = metaData.getImportedKeys(null, null, "test");

            while(columns.next()) {
                logger.info("foreign key : " + columns.getString("FKTABLE_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showIndexInfo() {
        logger.info("Retrieving indexes...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the indexes in the database
            ResultSet columns = metaData.getIndexInfo(null, null, "test", true, false);

            while(columns.next()) {
                logger.info("index : " + columns.getString("INDEX_NAME"));
            }
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }
}
