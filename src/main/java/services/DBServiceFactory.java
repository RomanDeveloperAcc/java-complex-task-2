package services;

import interfaces.DBService;
import models.DBConnectionData;

public class DBServiceFactory {
    public DBService createDbService(String type, DBConnectionData dbConnectionData) throws Exception {
        switch (type) {
            case "jdbc":
                return new MSSQLJDBCService(dbConnectionData);
            case "query":
                return new MSSQLQueryService(dbConnectionData);
            default:
                throw new Exception("Wrong service type");
        }
    }
}
