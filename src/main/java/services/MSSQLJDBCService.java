package services;

import interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MSSQLJDBCService implements DBService {
    private final String JDBC_URL;
    private final String DB_NAME;
    private final String DB_LOGIN;
    private final String DB_PASSWORD;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MSSQLJDBCService(String JDBC_URL, String DB_NAME, String DB_LOGIN, String DB_PASSWORD) {
        this.JDBC_URL = JDBC_URL;
        this.DB_NAME = DB_NAME;
        this.DB_LOGIN = DB_LOGIN;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public MSSQLJDBCService(String DB_NAME, String DB_LOGIN, String DB_PASSWORD) {
        this.DB_NAME = DB_NAME;
        this.JDBC_URL = "jdbc:sqlserver://localhost:1433;database=" + this.DB_NAME + ";trustServerCertificate=true";
        this.DB_LOGIN = DB_LOGIN;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public MSSQLJDBCService(String JDBC_URL, String DB_NAME) {
        this.JDBC_URL = JDBC_URL;
        this.DB_NAME = DB_NAME;
        this.DB_LOGIN = "yourlogin";
        this.DB_PASSWORD = "yourpassword";
    }

    public MSSQLJDBCService() {
        this.JDBC_URL = "jdbc:sqlserver://localhost:1433;database=TestDB;trustServerCertificate=true";
        this.DB_NAME = "TestDB";
        this.DB_LOGIN = "yourlogin";
        this.DB_PASSWORD = "yourpassword";
    }

    public void showTables() {
        logger.info("Retrieving tables...");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD)) {
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
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD)) {
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

    }

    public void showIndexInfo() {
        logger.info("Retrieving indexes...");
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD)) {
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
