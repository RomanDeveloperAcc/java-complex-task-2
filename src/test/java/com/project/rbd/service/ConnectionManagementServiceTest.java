package com.project.rbd.service;

import com.project.rbd.exception.ConnectionEstablishmentException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConnectionManagementServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getDBConnectionData() {
        String fileName = "test";

        ConnectionManagementService connectionManagementService = new ConnectionManagementService();

        exception.expect(ConnectionEstablishmentException.class);

        connectionManagementService.getDBConnectionData(fileName);
    }
}