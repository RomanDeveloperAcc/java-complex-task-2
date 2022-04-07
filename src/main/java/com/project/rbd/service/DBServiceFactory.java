package com.project.rbd.service;

import com.project.rbd.dto.db.DBConnectionData;
import com.project.rbd.exception.UnknownServiceTypeException;
import com.project.rbd.service.impl.MSSQLJDBCService;
import com.project.rbd.service.impl.MSSQLQueryService;

public class DBServiceFactory {
    public DBService createDbService(String type, DBConnectionData dbConnectionData) throws UnknownServiceTypeException {
        switch (type) {
            case "jdbc":
                return new MSSQLJDBCService(dbConnectionData);
            case "query":
                return new MSSQLQueryService(dbConnectionData);
            default:
                throw new UnknownServiceTypeException("Wrong service type");
        }
    }
}
