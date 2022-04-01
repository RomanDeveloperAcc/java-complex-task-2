package services;

import interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLJDBCService implements DBService {
    private final String jdbcUrl;
    private final String dbName;
    private final String dbLogin;
    private final String dbPassword;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLJDBCService(String jdbcUrl, String dbName, String dbLogin, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public MSSQLJDBCService(String dbName, String dbLogin, String dbPassword) {
        this.dbName = dbName;
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;database=" + this.dbName + ";trustServerCertificate=true";
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public MSSQLJDBCService(String jdbcUrl, String dbName) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.dbLogin = "yourlogin";
        this.dbPassword = "yourpassword";
    }

    public MSSQLJDBCService() {
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;database=TestDB;trustServerCertificate=true";
        this.dbName = "TestDB";
        this.dbLogin = "yourlogin";
        this.dbPassword = "yourpassword";
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
