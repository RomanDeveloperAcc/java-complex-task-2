package com.project.rbd.service;

import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.exception.ConnectionEstablishmentException;
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
        } catch (FileNotFoundException e) {
            String errorMessage = "Provided file is not found.";
            logger.error(errorMessage);
            throw new ConnectionEstablishmentException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = "Something went wrong while reading the file.";
            logger.error(errorMessage);
            throw new ConnectionEstablishmentException(errorMessage, e);
        }

        return dbConnectionData;
    }
}
