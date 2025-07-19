package net.weesli.rozsdblite.model;

import net.weesli.rozsdblite.io.FileBaseManagement;
import net.weesli.rozsdblite.other.DatabaseSettings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Database {

    private String databaseName;
    private Path databasePath;
    private DatabaseSettings databaseSettings;
    private FileBaseManagement asyncManagement;

    public Database(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        this.databaseName = databaseName;
        this.databasePath = new File(databasePath.toFile(), databaseName + ".rozsdb").toPath();
        if (!this.databasePath.toFile().exists()) {
            try {
                this.databasePath.toFile().createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.databaseSettings = databaseSettings;
        this.asyncManagement = new FileBaseManagement(this);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Path getDatabasePath() {
        return databasePath;
    }

    public DatabaseSettings getDatabaseSettings() {
        return databaseSettings;
    }

    public FileBaseManagement getAsyncManagement() {
        return asyncManagement;
    }
    public Table getTable(String tableName) {
        return new Table(this, tableName);
    }
}
