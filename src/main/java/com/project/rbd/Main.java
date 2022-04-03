package com.project.rbd;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;
import com.project.rbd.services.ConnectionManagementService;
import com.project.rbd.services.DBServiceFactory;

public class Main {
    public static void main(String[] args) {
        String fileName;
        String serviceType;
        DBConnectionData dbConnectionData;
//        if (args.length != 1) {
//            logger.error("Wrong amount of parameters");
//            System.exit(0);
//        }

        fileName = "db.properties";
        serviceType = "jdbc";
        ConnectionManagementService connectionManagementService = new ConnectionManagementService();
        dbConnectionData = connectionManagementService.getDBConnectionData(fileName);

        try {
            DBService mssqlQueryService = new DBServiceFactory().createDbService(serviceType, dbConnectionData);

            mssqlQueryService.showTables();
            mssqlQueryService.showColumns();
            mssqlQueryService.showConstraints();
            mssqlQueryService.showIndexInfo();
        } catch (Exception e) {
            System.out.println("The provided service type does not exist");
            System.exit(0);
        }
    }
}
