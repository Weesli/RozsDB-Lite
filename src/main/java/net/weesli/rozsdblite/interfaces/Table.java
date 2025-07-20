package net.weesli.rozsdblite.interfaces;

import java.util.List;

/**
 * Represents a table within a RozsDBLite database.
 * <p>
 * A Table stores key-value pairs where the key is an ID string and the value is a JSON string.
 * Provides basic CRUD operations on the data within the table.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * Table table = database.getTable("users");
 * table.put("user1", "{\"name\":\"Alice\"}");
 * String json = table.get("user1");
 * List<String> allData = table.getAll();
 * table.remove("user1");
 * </pre>
 *
 * @author  Weesli
 * @version 1.0.0
 */
public interface Table {

    /**
     * Returns the name of the table.
     *
     * @return the table name
     */
    String getTableName();

    /**
     * Returns the parent database that this table belongs to.
     *
     * @return the parent {@link Database}
     */
    Database getParent();

    /**
     * Inserts or updates a record in the table.
     *
     * @param id the unique identifier for the record
     * @param data the JSON string representing the record data
     * @return {@code true} if the operation succeeded, {@code false} otherwise
     */
    boolean put(String id, String data);

    /**
     * Retrieves the record data for the specified ID.
     *
     * @param id the unique identifier for the record
     * @return the JSON string representing the record data, or {@code null} if not found
     */
    String get(String id);

    /**
     * Returns all record data values in the table.
     *
     * @return a list of all JSON string data stored in the table
     */
    List<String> getAll();

    /**
     * Removes the record with the specified ID from the table.
     *
     * @param id the unique identifier for the record to remove
     */
    void remove(String id);
}
