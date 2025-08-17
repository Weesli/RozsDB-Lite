package net.weesli.rozsdblite.model;

import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.interfaces.Table;

import java.util.List;

public class TableImpl implements Table {
    private final Database parent;
    private final String tableName;

    public TableImpl(Database parent, String tableName) {
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
        if (obj instanceof TableImpl) {
            return tableName.equals(((TableImpl) obj).getTableName());
        }
        return false;
    }

    // usable methods for users

    public boolean put(String id, String data) {
        parent.getFileManagement().put(tableName, id, data);
        return true;
    }

    public String get(String id) {
        return parent.getFileManagement().get(tableName, id);
    }


    public List<String> getAll() {
        return parent.getFileManagement().getAll(tableName);
    }

    public void remove(String id) {
        parent.getFileManagement().delete(tableName, id);
    }

    @Override
    public Database getParent() {
        return parent;
    }
}
