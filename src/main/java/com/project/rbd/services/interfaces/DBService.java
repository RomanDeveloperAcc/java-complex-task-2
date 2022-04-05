package com.project.rbd.services.interfaces;

import java.util.ArrayList;

public interface DBService {
    public ArrayList<String> retrieveTables();
    public void showColumns(String tableName);
    public void showConstraints(String tableName);
    public void showIndexInfo(String tableName);
}
