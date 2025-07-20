package net.weesli.rozsdblite;

import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.model.DatabaseImpl;
import net.weesli.rozsdblite.other.DatabaseSettings;

import java.nio.file.Path;

public final class RozsDBLite {
    public static Database open(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        return new DatabaseImpl(databaseName, databasePath, databaseSettings);
    }
    public static Database open(String databaseName, Path databasePath) {
        return new DatabaseImpl(databaseName, databasePath, new DatabaseSettings());
    }
}