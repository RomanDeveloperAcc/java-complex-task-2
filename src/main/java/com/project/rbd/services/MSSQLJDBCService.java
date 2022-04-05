package com.project.rbd.services;

import com.project.rbd.services.interfaces.DBService;
import com.project.rbd.dto.db.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> retrieveTables() {
        List<String> tableNames = new ArrayList<>();

        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = { "TABLE" };

            //Retrieving the tables in the database
            ResultSet tables = metaData.getTables(null, null, null, types);

            while(tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
            logger.info("Retrieving tables COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return tableNames;
    }

    public List<String> showColumns(String tableName) {
        List<String> columnNames = new ArrayList<>();

        logger.info("Retrieving columns for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the columns in the database
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while(columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }
            logger.info("Retrieving columns COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return columnNames;
    }

    public List<String> showConstraints(String tableName) {
        List<String> constraintNames = new ArrayList<>();
        logger.info("Retrieving constraints for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            logger.info("Retrieving primary keys...");
            //Retrieving the constraints in the database
            ResultSet constraints = metaData.getPrimaryKeys(null, null, tableName);

            while(constraints.next()) {
                String constraintName = constraints.getString("PK_NAME");
                constraintNames.add(constraintName);
            }

            logger.info("Retrieving foreign keys...");

            constraints = metaData.getImportedKeys(null, null, tableName);

            while(constraints.next()) {
                String constraintName = constraints.getString("FKTABLE_NAME");
                constraintNames.add(constraintName);
            }
            logger.info("Retrieving constraints COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return constraintNames;
    }

    public List<String> showIndexInfo(String tableName) {
        List<String> indexNames = new ArrayList<>();
        logger.info("Retrieving indexes for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the indexes in the database
            ResultSet indexes = metaData.getIndexInfo(null, null, tableName, true, false);

            while(indexes.next()) {
                if (indexes.getString("INDEX_NAME") != null) {
                    String indexName = indexes.getString("INDEX_NAME");
                    indexNames.add(indexName);
                }
            }
            logger.info("Retrieving indexes COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return indexNames;
    }
}
