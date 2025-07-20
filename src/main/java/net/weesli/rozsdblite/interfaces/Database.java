package net.weesli.rozsdblite.interfaces;

import net.weesli.rozsdblite.io.FileManagement;
import net.weesli.rozsdblite.other.DatabaseSettings;

import java.nio.file.Path;

/**
 * Represents a general interface for a RozsDB database.
 * <p>
 * A Database instance manages access to database files, settings, and tables.
 * It also provides mechanisms to read/write data via {@link FileManagement}.
 * </p>
 *
 * <p>Typical usage:</p>
 * <pre>
 *
 * Database db = new DatabaseImpl("myDB", Path.of("/path/to/db"), settings);
 * Table table = db.getTable("users");
 * table.put("id1", "{...}");
 * db.save(); // flush changes to disk
 *
 * </pre>
 *
 * @author  Weesli
 * @version 1.1.0
 * @see FileManagement
 * @see Table
 */
public interface Database {

    /**
     * Returns the name of the database.
     *
     * @return the database name
     */
    String getDatabaseName();

    /**
     * Returns the path to the database file on disk.
     *
     * @return the database file path
     */
    Path getDatabasePath();

    /**
     * Returns the settings associated with this database.
     *
     * @return the database settings
     */
    DatabaseSettings getDatabaseSettings();

    /**
     * Provides access to the {@link FileManagement} instance
     * which handles all I/O operations (read/write).
     *
     * @return the FileManagement instance
     */
    FileManagement getFileManagement();

    /**
     * Returns a {@link Table} with the specified name.
     * <p>
     * If the table does not exist, it will be automatically created.
     * </p>
     *
     * @param tableName the name of the table
     * @return the {@link Table} instance
     */
    Table getTable(String tableName);

    /**
     * Persists all in-memory changes to the database file on disk.
     * <p>
     * This operation will flush all pending writes and ensure data consistency.
     * </p>
     */
    void save();
}
