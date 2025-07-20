package net.weesli.rozsdblite.io;

import net.weesli.rozsdblite.model.DatabaseImpl;
import java.util.*;

public class FileManagement {

    private Writer writer;
    private Reader reader;
    private DatabaseImpl database;

    private final HashMap<String, LinkedHashMap<String, String>> cache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public FileManagement(DatabaseImpl database) {
        this.database = database;
        writer = new Writer(database, this);
        reader = new Reader(database, this);
        try {
            cache.putAll(reader.read());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void put(String tableName, String id, String data) {
        try {
            LinkedHashMap<String, String> map = cache.computeIfAbsent(tableName, k -> new LinkedHashMap<>());
            map.put(id, data);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String tableName, String id) {
        try {
            LinkedHashMap<String, String> map = cache.computeIfAbsent(tableName, k -> new LinkedHashMap<>());
            map.remove(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String get(String tableName, String id) {
        try {
            return cache.computeIfAbsent(tableName, k -> new LinkedHashMap<>()).get(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        writer.flush();
    }

    public List<String> getAll(String tableName) {
        try {
            return new ArrayList<>(cache.computeIfAbsent(tableName, k -> new LinkedHashMap<>()).values());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, LinkedHashMap<String, String>> getCache() {
        return cache;
    }
}
