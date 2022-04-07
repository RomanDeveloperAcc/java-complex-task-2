package com.project.rbd.service.impl;

import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getTables() {
        List<String> tableNames = new ArrayList<>();

        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("select * from INFORMATION_SCHEMA.TABLES");
        ) {
            //Retrieving the tables in the database
            ResultSet tables = statement.executeQuery();

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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("select * from INFORMATION_SCHEMA.VIEWS");
        ) {
            //Retrieving the views in the database
            ResultSet views = statement.executeQuery();

            while(views.next()) {
                String viewName = views.getString("VIEW_NAME");
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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("select * from INFORMATION_SCHEMA.COLUMNS where table_name = '" + tableName + "'");
        ) {
            //Retrieving the columns in the database
            ResultSet columns = statement.executeQuery();

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
            PreparedStatement statement = connection.prepareStatement("select * from sys.key_constraints where object_id = object_id('" + tableName + "')");
            //Retrieving the constraints in the database
            ResultSet constraints = statement.executeQuery();

            while(constraints.next()) {
                String constraintName = constraints.getString("name");
                constraintNames.add(constraintName);
            }

            statement = connection.prepareStatement("select * from sys.foreign_keys where object_id = object_id('" + tableName + "')");
            constraints = statement.executeQuery();

            while(constraints.next()) {
                String constraintName = constraints.getString("name");
                constraintNames.add(constraintName);
            }

            logger.info("Retrieving constraints COMPLETED");
            statement.close();
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return constraintNames;
    }

    public List<String> getIndexes(String tableName) {
        List<String> indexNames = new ArrayList<>();
        logger.info("Retrieving indexes for table " + tableName + "...");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("select * from sys.indexes where object_id = object_id('" + tableName + "')");
        ) {
            //Retrieving the indexes in the database
            ResultSet indexes = statement.executeQuery();

            while(indexes.next()) {
                if (indexes.getString("name") != null) {
                    String indexName = indexes.getString("name");
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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("\n" +
                     "\n" +
                     "SELECT name, type_desc \n" +
                     "  FROM sys.sql_modules m \n" +
                     "INNER JOIN sys.objects o \n" +
                     "        ON m.object_id=o.object_id\n" +
                     "WHERE type_desc like '%function%'\n" +
                     "\n");
        ) {
            //Retrieving the functions in the database
            ResultSet functions = statement.executeQuery();

            while(functions.next()) {
                String functionName = functions.getString("name");
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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("Select [NAME] from sysobjects where type = 'P' and category = 0");
        ) {
            //Retrieving the procedures in the database
            ResultSet procedures = statement.executeQuery();

            while(procedures.next()) {
                String procedureName = procedures.getString("NAME");
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
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
             PreparedStatement statement = connection.prepareStatement("SELECT  \n" +
                     "    name,\n" +
                     "    is_instead_of_trigger\n" +
                     "FROM \n" +
                     "    sys.triggers  \n" +
                     "WHERE \n" +
                     "    type = 'TR'");
        ) {
            //Retrieving the triggers in the database
            ResultSet triggers = statement.executeQuery();

            while(triggers.next()) {
                String triggerName = triggers.getString("NAME");
                triggerNames.add(triggerName);
            }
            logger.info("Retrieving triggers COMPLETED");
        } catch (SQLException e) {
            logger.error("Something went wrong. " + e.getMessage());
        }

        return triggerNames;
    }
}
