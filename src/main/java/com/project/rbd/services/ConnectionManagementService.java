package com.project.rbd.services;

import com.project.rbd.models.DBConnectionData;
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
            logger.error("Provided file is not found. Default properties will be used.");

            try (FileInputStream fis = new FileInputStream("src/main/resources/" + fileName)) {
                prop.load(fis);
                dbConnectionData.jdbcUrl = prop.getProperty("jdbc.url");
                dbConnectionData.dbName = prop.getProperty("db.name");
                dbConnectionData.dbLogin = prop.getProperty("db.login");
                dbConnectionData.dbPassword = prop.getProperty("db.password");
            } catch (IOException e) {
                logger.error("Something went wrong while reading the file. Default properties will be used.");
            }
        } catch (IOException ex) {
            logger.error("Something went wrong while reading the file. Default properties will be used.");
        }

        return dbConnectionData;
    }
}
