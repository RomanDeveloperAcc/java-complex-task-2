package com.project.rbd.services;

import com.project.rbd.interfaces.DBService;
import com.project.rbd.models.DBConnectionData;

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
