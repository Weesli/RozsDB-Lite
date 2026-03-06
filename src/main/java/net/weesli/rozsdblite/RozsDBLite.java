package net.weesli.rozsdblite;

import com.google.gson.Gson;
import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.model.DatabaseImpl;
import net.weesli.rozsdblite.other.DatabaseSettings;

import java.nio.file.Path;

public final class RozsDBLite {

    private static Gson gson;

    public static Database open(String databaseName, Path databasePath, DatabaseSettings databaseSettings) {
        return new DatabaseImpl(databaseName, databasePath, databaseSettings);
    }
    public static Database open(String databaseName, Path databasePath) {
        return new DatabaseImpl(databaseName, databasePath, DatabaseSettings.empty());
    }

    public static Gson getGson(){
        return gson;
    }

    public static void injectGson(Gson gson){
        RozsDBLite.gson = gson;
    }
}