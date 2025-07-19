package net.weesli;

import net.weesli.model.Database;
import net.weesli.other.DatabaseSettings;

import java.nio.file.Path;

public final class RozsDBLite {
    public static Database open(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        return new Database(databaseName, databasePath, databaseSettings);
    }
    public static Database open(String databaseName, Path databasePath) {
        return new Database(databaseName, databasePath, new DatabaseSettings());
    }
}