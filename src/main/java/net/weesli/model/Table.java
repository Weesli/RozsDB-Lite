package net.weesli.model;

import java.util.List;

public class Table {
    private Database parent;
    private String tableName;

    public Table(Database parent,String tableName) {
        this.tableName = tableName;
        this.parent = parent;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public int hashCode() {
        return tableName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Table) {
            return tableName.equals(((Table) obj).getTableName());
        }
        return false;
    }

    // table objects management

    public boolean put(String id, String data) {
        parent.getAsyncManagement().put(tableName, id, data);
        return true;
    }
    public String get(String id) {
        return parent.getAsyncManagement().get(tableName, id);
    }
    public List<String> getAll() {
        return parent.getAsyncManagement().getAll(tableName);
    }
    public void remove(String id) {
        parent.getAsyncManagement().delete(tableName, id);
    }

}
