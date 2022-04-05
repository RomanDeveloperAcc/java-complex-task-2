package com.project.rbd.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EntityPrinterService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void showEntityData(String entityName, List<String> data) {
        if (data.size() > 0) {
            logger.info("Printing " + entityName + " : ");

            for (String element : data) {
                logger.info(element);
            }
        } else {
            logger.info("No " + entityName + " to print");
        }
    }
}
