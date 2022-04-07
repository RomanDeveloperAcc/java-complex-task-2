package com.project.rbd.service.impl;

import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.service.DBService;
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

    public List<String> getTables() {
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

    public List<String> getViews() {
        List<String> viewNames = new ArrayList<>();

        logger.info("Retrieving views...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = { "VIEW" };

            //Retrieving the views in the database
            ResultSet views = metaData.getTables(null, null, null, types);

            while(views.next()) {
                String viewName = views.getString("TABLE_NAME");
                viewNames.add(viewName);
            }
            logger.info("Retrieving views COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return viewNames;
    }

    public List<String> getColumns(String tableName) {
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

    public List<String> getConstraints(String tableName) {
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

    public List<String> getIndexes(String tableName) {
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

    public List<String> getFunctions() {
        List<String> functionNames = new ArrayList<>();

        logger.info("Retrieving functions...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the functions in the database
            ResultSet functions = metaData.getFunctions(null, null, null);

            while(functions.next()) {
                String functionName = functions.getString("FUNCTION_NAME");
                functionNames.add(functionName);
            }
            logger.info("Retrieving functions COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return functionNames;
    }

    public List<String> getProcedures() {
        List<String> procedureNames = new ArrayList<>();

        logger.info("Retrieving procedures...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();

            //Retrieving the procedures in the database
            ResultSet procedures = metaData.getProcedures(null, null, null);

            while(procedures.next()) {
                String procedureName = procedures.getString("PROCEDURE_NAME");
                procedureNames.add(procedureName);
            }
            logger.info("Retrieving procedures COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return procedureNames;
    }

    public List<String> getTriggers() {
        List<String> triggerNames = new ArrayList<>();

        logger.info("Retrieving triggers...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = { "TRIGGER" };

            //Retrieving the triggers in the database
            ResultSet triggers = metaData.getTables(null, null, null, types);

            while(triggers.next()) {
                String triggerName = triggers.getString("TABLE_NAME");
                triggerNames.add(triggerName);
            }
            logger.info("Retrieving tables COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return triggerNames;
    }
}
