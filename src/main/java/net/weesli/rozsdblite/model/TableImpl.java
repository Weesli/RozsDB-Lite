package net.weesli.rozsdblite.model;

import net.weesli.rozsdblite.RozsDBLite;
import net.weesli.rozsdblite.interfaces.Database;
import net.weesli.rozsdblite.interfaces.Table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TableImpl<T> implements Table<T> {

    private final Database parent;
    private final String tableName;
    private final Class<T> classOfT;

    private final LinkedHashMap<String, T> cache = new LinkedHashMap<>();

    public TableImpl(Database parent, String tableName, Class<T> classOfT) {
        this.parent = parent;
        this.tableName = tableName;
        this.classOfT = classOfT;
        try {
            LinkedHashMap<String,String> data = parent.getFileManagement().getReader().read().getOrDefault(tableName, new LinkedHashMap<>());
            data.forEach((key, value) -> {
                T obj = RozsDBLite.getGson().fromJson(value, classOfT);
                cache.put(key,obj);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        return tableName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TableImpl<?>) {
            return tableName.equals(((TableImpl<?>) obj).tableName());
        }
        return false;
    }

    // usable methods for users

    @Override
    public String tableName() {
        return tableName;
    }

    @Override
    public Database parent() {
        return parent;
    }

    public boolean put(String id, T data) {
        cache.put(id,data);
        return true;
    }

    public T get(String id) {
        return cache.get(id);
    }

    public T getOrPut(String id, T data){
        return cache.computeIfAbsent(id, k-> data);
    }

    public List<T> getAll() {
        return new ArrayList<>(cache.values());
    }

    public void remove(String id) {
        cache.remove(id);
    }

    @Override
    public LinkedHashMap<String, T> cache() {
        return cache;
    }
}
