package services;

import interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLQueryService implements DBService {
    private final String jdbcUrl;
    private final String dbName;
    private final String dbLogin;
    private final String dbPassword;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLQueryService(String jdbcUrl, String dbName, String dbLogin, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public MSSQLQueryService(String dbName, String dbLogin, String dbPassword) {
        this.dbName = dbName;
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;database=" + this.dbName + ";trustServerCertificate=true";
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
    }

    public MSSQLQueryService(String jdbcUrl, String dbName) {
        this.jdbcUrl = jdbcUrl;
        this.dbName = dbName;
        this.dbLogin = "yourlogin";
        this.dbPassword = "yourpassword";
    }

    public MSSQLQueryService() {
        this.jdbcUrl = "jdbc:sqlserver://localhost:1433;database=TestDB;trustServerCertificate=true";
        this.dbName = "TestDB";
        this.dbLogin = "yourlogin";
        this.dbPassword = "yourpassword";
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
