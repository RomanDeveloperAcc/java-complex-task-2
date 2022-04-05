package com.project.rbd.services;

import com.project.rbd.dto.db.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConnectionManagementService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DBConnectionData getDBConnectionData(String fileName) {
        DBConnectionData dbConnectionData = new DBConnectionData();
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            dbConnectionData.jdbcUrl = prop.getProperty("jdbc.url");
            dbConnectionData.dbName = prop.getProperty("db.name");
            dbConnectionData.dbLogin = prop.getProperty("db.login");
            dbConnectionData.dbPassword = prop.getProperty("db.password");
        } catch (FileNotFoundException ex) {
            logger.error("Provided file is not found.");
            System.exit(0);
        } catch (IOException ex) {
            logger.error("Something went wrong while reading the file.");
        }

        return dbConnectionData;
    }
}
