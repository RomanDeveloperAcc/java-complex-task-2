package com.project.rbd;

import com.project.rbd.service.EntityPrinterService;
import com.project.rbd.service.DBService;
import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.service.ConnectionManagementService;
import com.project.rbd.service.DBServiceFactory;
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

            List<String> tables = mssqlQueryService.getTables();
            List<String> views = mssqlQueryService.getViews();

            EntityPrinterService entityPrinter = new EntityPrinterService();

            entityPrinter.showEntityData("tables", tables);
            entityPrinter.showEntityData("views", views);

            for (String table : tables) {
                logger.info("Printing information for table " + table);

                List<String> columns = mssqlQueryService.getColumns(table);
                List<String> constraints = mssqlQueryService.getConstraints(table);
                List<String> indexes = mssqlQueryService.getIndexes(table);

                entityPrinter.showEntityData("columns", columns);
                entityPrinter.showEntityData("constraints", constraints);
                entityPrinter.showEntityData("indexes", indexes);
            }

            for (String view : views) {
                logger.info("Printing information for view " + view);

                List<String> columns = mssqlQueryService.getColumns(view);
                List<String> constraints = mssqlQueryService.getConstraints(view);
                List<String> indexes = mssqlQueryService.getIndexes(view);

                entityPrinter.showEntityData("columns", columns);
                entityPrinter.showEntityData("constraints", constraints);
                entityPrinter.showEntityData("indexes", indexes);
            }

            List<String> functions = mssqlQueryService.getFunctions();
            List<String> procedures = mssqlQueryService.getProcedures();
            List<String> triggers = mssqlQueryService.getTriggers();

            entityPrinter.showEntityData("functions", functions);
            entityPrinter.showEntityData("procedures", procedures);
            entityPrinter.showEntityData("triggers", triggers);
        } catch (Exception e) {
            logger.error("The provided service type does not exist");
            System.exit(0);
        }
    }
}
