package net.weesli.rozsdblite.model;

import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.interfaces.Table;
import net.weesli.rozsdblite.io.FileManagement;
import net.weesli.rozsdblite.other.DatabaseSettings;
import net.weesli.rozsdblite.scheduler.RozsScheduler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements Database {

    private final String databaseName;
    private final Path databasePath;
    private final DatabaseSettings databaseSettings;
    private final FileManagement fileManagement;
    private final List<Table<?>> tables = new ArrayList<>();

    public DatabaseImpl(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        this.databaseName = databaseName;
        this.databasePath = databasePath.resolve(databaseName + ".rozsdb");

        try {
            Path parentDir = this.databasePath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            if (!Files.exists(this.databasePath)) {
                Files.createFile(this.databasePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    @SuppressWarnings("unchecked")
    @Override
    public <T> Table<T> getTable(String tableName, Class<T> classOfT) {
        return (Table<T>) tables.stream()
                .filter(table -> table.tableName().equalsIgnoreCase(tableName))
                .findFirst()
                .orElseGet(() -> {
                    TableImpl<T> newTable = new TableImpl<>(this, tableName, classOfT);
                    tables.add(newTable);
                    return newTable;
                });
    }

    public List<Table<?>> getAllTables(){
        return tables;
    }
    public void save(){
        fileManagement.save();
    }
    public void close(){
        if (databaseSettings.isAutoSave()) RozsScheduler.close();
    }
}
