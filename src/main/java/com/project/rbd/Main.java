package com.project.rbd;

import com.project.rbd.services.EntityPrinterService;
import com.project.rbd.services.interfaces.DBService;
import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.services.ConnectionManagementService;
import com.project.rbd.services.DBServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

            List<String> tables = mssqlQueryService.retrieveTables();
            EntityPrinterService entityPrinter = new EntityPrinterService();

            entityPrinter.showEntityData("tables", tables);

            for (String table : tables) {
                List<String> columns = mssqlQueryService.showColumns(table);
                List<String> constraints = mssqlQueryService.showConstraints(table);
                List<String> indexes = mssqlQueryService.showIndexInfo(table);

                entityPrinter.showEntityData("columns", columns);
                entityPrinter.showEntityData("constraints", constraints);
                entityPrinter.showEntityData("indexes", indexes);
            }
        } catch (Exception e) {
            logger.error("The provided service type does not exist");
            System.exit(0);
        }
    }
}
