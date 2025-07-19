package net.weesli.rozsdblite.io;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import net.weesli.rozsdblite.model.Database;
import net.weesli.rozsdblite.util.CompressUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;

public class DatabaseWriteQueue {

    private static final DslJson<HashMap<String, LinkedHashMap<String, String>>> DslJson = new DslJson<>();

    private ExecutorService executorService;
    private FileManagement fileManagement;
    private HashMap<String, LinkedHashMap<String, String>> writeQueue = new HashMap<>();
    private Database database;
    public DatabaseWriteQueue(ExecutorService executorService,FileManagement fileManagement, Database database) {
        this.executorService = executorService;
        this.fileManagement = fileManagement;
        this.database = database;
    }

    public LinkedHashMap<String, String> getWriteQueue(String tableName) {
        return writeQueue.computeIfAbsent(tableName, k -> new LinkedHashMap<>());
    }

    public void flush() {
        executorService.submit(() -> {
            JsonWriter jsonWriter = DslJson.newWriter();
            try {
                DslJson.serialize(jsonWriter, writeQueue);
                byte[] compressed = CompressUtil.compress(jsonWriter.toByteArray());
                fileManagement.writeDatabaseFile(database.getDatabasePath(), compressed);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public HashMap<String, LinkedHashMap<String, String>> getWriteQueue() {
        return writeQueue;
    }
}
