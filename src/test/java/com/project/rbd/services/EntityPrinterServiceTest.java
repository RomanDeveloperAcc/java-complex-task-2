package com.project.rbd.services;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EntityPrinterServiceTest {
    @Test
    public void showEntityData() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        EntityPrinterService entityPrinter = new EntityPrinterService();

        String testEntityName = "test";

        List<String> testList = new ArrayList<>();
        testList.add("first");
        testList.add("second");

        entityPrinter.showEntityData(testEntityName, testList);

        assertNotNull(outContent.toString());
    }
}