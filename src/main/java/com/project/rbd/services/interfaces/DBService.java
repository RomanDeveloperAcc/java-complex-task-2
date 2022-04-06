package com.project.rbd.services.interfaces;

import java.util.List;

public interface DBService {
    public List<String> getTables();
    public List<String> getViews();
    public List<String> getColumns(String tableName);
    public List<String> getConstraints(String tableName);
    public List<String> getIndexes(String tableName);
    public List<String> getFunctions();
    public List<String> getProcedures();
    public List<String> getTriggers();
}
