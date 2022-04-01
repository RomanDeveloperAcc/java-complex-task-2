import interfaces.DBService;
import models.DBConnectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.ConnectionManagementService;
import services.MSSQLJDBCService;
import services.MSSQLQueryService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String fileName;
        String jdbcUrl = null;
        String dbName = null;
        String dbLogin = null;
        String dbPassword = null;

//        if (args.length != 1) {
//            logger.error("Wrong amount of parameters");
//            System.exit(0);
//        }

        ConnectionManagementService connectionManagementService = new ConnectionManagementService();
        DBConnectionData dbConnectionData = connectionManagementService.getDBConnectionData("db.properties");


        DBService mssqlQueryService = new MSSQLJDBCService(dbConnectionData);

        mssqlQueryService.showTables();
        mssqlQueryService.showColumns();
        mssqlQueryService.showConstraints();
        mssqlQueryService.showIndexInfo();
    }
}
