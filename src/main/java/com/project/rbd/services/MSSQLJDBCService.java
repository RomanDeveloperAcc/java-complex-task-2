package com.project.rbd.services;

import com.project.rbd.services.interfaces.DBService;
import com.project.rbd.dto.db.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<String> retrieveTables() {
        ArrayList<String> tableNames = new ArrayList<>();

        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};

            //Retrieving the tables in the database
            ResultSet tables = metaData.getTables(null, null, null, types);

            while(tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                logger.info("table : " + tableName);
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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the columns in the database
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while(columns.next()) {
                logger.info("column : " + columns.getString("COLUMN_NAME"));
            }
            logger.info("Retrieving columns COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showConstraints(String tableName) {
        logger.info("Retrieving constraints for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            logger.info("Retrieving primary keys...");
            //Retrieving the constraints in the database
            ResultSet columns = metaData.getPrimaryKeys(null, null, tableName);

            while(columns.next()) {
                logger.info("primary key : " + columns.getString("PK_NAME"));
            }

            logger.info("Retrieving foreign keys...");

            columns = metaData.getImportedKeys(null, null, tableName);

            while(columns.next()) {
                logger.info("foreign key : " + columns.getString("FKTABLE_NAME"));
            }
            logger.info("Retrieving constraints COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }

    public void showIndexInfo(String tableName) {
        logger.info("Retrieving indexes for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the indexes in the database
            ResultSet columns = metaData.getIndexInfo(null, null, tableName, true, false);

            while(columns.next()) {
                if (columns.getString("INDEX_NAME") != null) {
                    logger.info("index : " + columns.getString("INDEX_NAME"));
                }
            }
            logger.info("Retrieving indexes COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }
    }
}
