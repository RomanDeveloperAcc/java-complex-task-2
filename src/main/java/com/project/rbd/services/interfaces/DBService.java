package com.project.rbd.services.interfaces;

import java.util.List;

public interface DBService {
    public List<String> retrieveTables();
    public void showColumns(String tableName);
    public void showConstraints(String tableName);
    public void showIndexInfo(String tableName);
}
