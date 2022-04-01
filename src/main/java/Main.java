import interfaces.DBService;
import models.DBConnectionData;
import services.ConnectionManagementService;
import services.DBServiceFactory;
import services.MSSQLJDBCService;

public class Main {
    public static void main(String[] args) {
        String fileName;
        String serviceType;
//        if (args.length != 1) {
//            logger.error("Wrong amount of parameters");
//            System.exit(0);
//        }

        fileName = "db.properties";
        serviceType = "query";
        ConnectionManagementService connectionManagementService = new ConnectionManagementService();
        DBConnectionData dbConnectionData = connectionManagementService.getDBConnectionData(fileName);

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
