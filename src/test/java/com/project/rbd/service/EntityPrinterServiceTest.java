package com.project.rbd.service;

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
        String firstTestListValue = "first";
        String secondTestListValue = "second";

        List<String> testList = new ArrayList<>();
        testList.add(firstTestListValue);
        testList.add(secondTestListValue);

        entityPrinter.showEntityData(testEntityName, testList);

        assertTrue(outContent.toString().contains(testEntityName));
        assertTrue(outContent.toString().contains(firstTestListValue));
        assertTrue(outContent.toString().contains(secondTestListValue));
    }
}