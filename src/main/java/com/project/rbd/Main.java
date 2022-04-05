package com.project.rbd;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;
import com.project.rbd.services.ConnectionManagementService;
import com.project.rbd.services.DBServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String source;
        String serviceType;
        DBConnectionData dbConnectionData;

        if (args.length != 2) {
            logger.error("Wrong amount of parameters");
            System.exit(0);
        }

        source = args[0];
        serviceType = args[1];

        boolean isJdbcUrl = source.split(":")[0].equals("jdbc");

        if (isJdbcUrl) {
            dbConnectionData = new DBConnectionData();
            dbConnectionData.jdbcUrl = source;
        } else {
            ConnectionManagementService connectionManagementService = new ConnectionManagementService();
            dbConnectionData = connectionManagementService.getDBConnectionData(source);
        }


        try {
            DBService mssqlQueryService = new DBServiceFactory().createDbService(serviceType, dbConnectionData);

            ArrayList<String> tables = mssqlQueryService.retrieveTables();

            for (String table : tables) {
                mssqlQueryService.showColumns(table);
                mssqlQueryService.showConstraints(table);
                mssqlQueryService.showIndexInfo(table);
            }
        } catch (Exception e) {
            logger.error("The provided service type does not exist");
            System.exit(0);
        }
    }
}
