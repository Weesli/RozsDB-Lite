package net.weesli.rozsdblite.io;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import net.weesli.rozsdblite.model.Database;
import net.weesli.rozsdblite.util.CompressUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncManagement {

    private static final DslJson<HashMap<String, LinkedHashMap<String, String>>> DslJson = new DslJson<>();

    private DatabaseWriteQueue databaseWriteQueue;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private FileManagement fileManagement = new FileManagement();
    private Database database;

    @SuppressWarnings("unchecked")
    public AsyncManagement(Database database) {
        this.database = database;
        databaseWriteQueue = new DatabaseWriteQueue(executorService, fileManagement, database);
        // read all database files
        byte[] value = fileManagement.readDatabaseFile(database.getDatabasePath());
        if (value.length == 0 || value == null) {
            return;
        }
        byte[] data = CompressUtil.decompress(value);
        if (data.length != 0) {
            JsonReader<HashMap<String, LinkedHashMap<String, String>>> jsonReader = DslJson.newReader(data).process(data, data.length);
            try {
                HashMap<String, LinkedHashMap<String, String>> map = jsonReader.next(HashMap.class);
                for (String tableName : map.keySet()) {
                    for (Map.Entry<String, String> id : map.get(tableName).entrySet()) {
                        databaseWriteQueue.getWriteQueue(tableName).put(id.getKey(), id.getValue());
                    }
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void put(String tableName, String id, String data) {
        LinkedHashMap<String, String> map = databaseWriteQueue.getWriteQueue(tableName);
        map.put(id, data);
        databaseWriteQueue.flush();
    }

    public void delete(String tableName, String id) {
        LinkedHashMap<String, String> map = databaseWriteQueue.getWriteQueue(tableName);
        map.remove(id);
        databaseWriteQueue.flush();
    }
    public String get(String tableName, String id) {
        LinkedHashMap<String, String> map = databaseWriteQueue.getWriteQueue(tableName);
        return map.get(id);
    }

    public List<String> getAll(String tableName) {
        LinkedHashMap<String, String> map = databaseWriteQueue.getWriteQueue(tableName);
        return map.values().stream().toList();
    }
}
