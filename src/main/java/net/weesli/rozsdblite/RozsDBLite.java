package net.weesli.rozsdblite;

import net.weesli.rozsdblite.model.Database;
import net.weesli.rozsdblite.other.DatabaseSettings;

import java.nio.file.Path;

public final class RozsDBLite {
    public static Database open(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        return new Database(databaseName, databasePath, databaseSettings);
    }
    public static Database open(String databaseName, Path databasePath) {
        return new Database(databaseName, databasePath, new DatabaseSettings());
    }
}