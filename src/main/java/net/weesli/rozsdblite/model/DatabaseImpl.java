package net.weesli.rozsdblite.model;

import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.io.FileManagement;
import net.weesli.rozsdblite.other.DatabaseSettings;
import net.weesli.rozsdblite.scheduler.RozsScheduler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DatabaseImpl implements Database {

    private final String databaseName;
    private final Path databasePath;
    private final DatabaseSettings databaseSettings;
    private final FileManagement fileManagement;

    public DatabaseImpl(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
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
        this.fileManagement = new FileManagement(this);

        // auto save is enabled start a task for it
        if (databaseSettings.isAutoSave()) RozsScheduler.open(this);
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

    public FileManagement getFileManagement() {
        return fileManagement;
    }

    // usable methods for users

    public TableImpl getTable(String tableName) {
        return new TableImpl(this, tableName);
    }
    public void save(){
        fileManagement.save();
    }
    public void close(){
        if (databaseSettings.isAutoSave()) RozsScheduler.close();
    }
}
