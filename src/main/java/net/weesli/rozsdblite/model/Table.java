package net.weesli.rozsdblite.model;

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

    // usable methods for users

    /**
     * Method for sending data to the print queue
     * @param id
     * @param data
     * @return
     */
    public boolean put(String id, String data) {
        parent.getAsyncManagement().put(tableName, id, data);
        return true;
    }

    /**
     * Method for getting data from the print queue
     * @param id
     * @return
     */
    public String get(String id) {
        return parent.getAsyncManagement().get(tableName, id);
    }

    /**
     * Method for getting all data from the print queue
     * @return
     */
    public List<String> getAll() {
        return parent.getAsyncManagement().getAll(tableName);
    }

    /**
     * Method for removing data from the print queue
     * @param id
     */
    public void remove(String id) {
        parent.getAsyncManagement().delete(tableName, id);
    }

}
