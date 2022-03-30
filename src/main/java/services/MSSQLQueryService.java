package services;

import interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLQueryService implements DBService {
    private final String JDBC_URL;
    private final String DB_NAME;
    private final String DB_LOGIN;
    private final String DB_PASSWORD;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLQueryService(String JDBC_URL, String DB_NAME, String DB_LOGIN, String DB_PASSWORD) {
        this.JDBC_URL = JDBC_URL;
        this.DB_NAME = DB_NAME;
        this.DB_LOGIN = DB_LOGIN;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public MSSQLQueryService(String DB_NAME, String DB_LOGIN, String DB_PASSWORD) {
        this.DB_NAME = DB_NAME;
        this.JDBC_URL = "jdbc:sqlserver://localhost:1433;database=" + this.DB_NAME + ";trustServerCertificate=true";
        this.DB_LOGIN = DB_LOGIN;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public MSSQLQueryService(String JDBC_URL, String DB_NAME) {
        this.JDBC_URL = JDBC_URL;
        this.DB_NAME = DB_NAME;
        this.DB_LOGIN = "yourlogin";
        this.DB_PASSWORD = "yourpassword";
    }

    public MSSQLQueryService() {
        this.JDBC_URL = "jdbc:sqlserver://localhost:1433;database=TestDB;trustServerCertificate=true";
        this.DB_NAME = "TestDB";
        this.DB_LOGIN = "yourlogin";
        this.DB_PASSWORD = "yourpassword";
    }

    public void showTables() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD);
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
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD);
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

    }

    public void showIndexInfo() {

    }
}
