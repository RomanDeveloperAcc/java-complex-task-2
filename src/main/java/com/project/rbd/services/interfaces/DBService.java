package com.project.rbd.services.interfaces;

import java.util.List;

public interface DBService {
    public List<String> retrieveTables();
    public List<String> showColumns(String tableName);
    public List<String> showConstraints(String tableName);
    public List<String> showIndexInfo(String tableName);
}
