import interfaces.DBService;
import models.DBConnectionData;
import services.ConnectionManagementService;
import services.MSSQLJDBCService;

public class Main {
    public static void main(String[] args) {
        String fileName;
//        if (args.length != 1) {
//            logger.error("Wrong amount of parameters");
//            System.exit(0);
//        }

        fileName = "db.properties";
        ConnectionManagementService connectionManagementService = new ConnectionManagementService();
        DBConnectionData dbConnectionData = connectionManagementService.getDBConnectionData(fileName);


        DBService mssqlQueryService = new MSSQLJDBCService(dbConnectionData);

        mssqlQueryService.showTables();
        mssqlQueryService.showColumns();
        mssqlQueryService.showConstraints();
        mssqlQueryService.showIndexInfo();
    }
}
