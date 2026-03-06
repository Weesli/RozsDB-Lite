package net.weesli.rozsdblite.io;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import net.weesli.rozsdblite.RozsDBLite;
import net.weesli.rozsdblite.interfaces.Table;
import net.weesli.rozsdblite.model.DatabaseImpl;
import net.weesli.rozsdblite.util.CompressUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Writer {

    private static final DslJson<HashMap<String, LinkedHashMap<String, String>>> DslJson = new DslJson<>();
    private final DatabaseImpl database;
    private final FileManagement fileManagement;

    public Writer(DatabaseImpl database, FileManagement fileManagement) {
        this.database = database;
        this.fileManagement = fileManagement;
    }

    public void flush() {
        try {
            Map<String, LinkedHashMap<String, String>> data = new HashMap<>();

            for (Table<?> table : database.getAllTables()) {
                LinkedHashMap<String, String> jsonValues = table.cache().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> RozsDBLite.getGson().toJson(entry.getValue()),
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        ));
                data.put(table.tableName(), jsonValues);
            }
            JsonWriter jsonWriter = DslJson.newWriter();
            DslJson.serialize(jsonWriter, data);
            byte[] compressed = CompressUtil.compress(jsonWriter.toByteArray());
            writeFileInParallel(database.getDatabasePath(), compressed, 10).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static CompletableFuture<Void> writeFileInParallel(Path path, byte[] data, int numParts) throws IOException {
        long fileSize = data.length;
        long chunkSize = (fileSize + numParts - 1) / numParts;
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numParts; i++) {
            long start = i * chunkSize;
            int size = (int) Math.min(chunkSize, fileSize - start);
            if (size > 0) {
                futures.add(writeChunkAsync(channel, data, start, size));
            }
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((v, ex) -> {
                    try {
                        channel.close();
                    } catch (IOException ignored) {
                    }
                });
    }

    private static CompletableFuture<Void> writeChunkAsync(AsynchronousFileChannel channel, byte[] data, long position, int size) {
        ByteBuffer buffer = ByteBuffer.wrap(data, (int) position, size);
        CompletableFuture<Void> future = new CompletableFuture<>();
        channel.write(buffer, position, null, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Object attachment) {
                future.complete(null);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                future.completeExceptionally(exc);
            }
        });
        return future;
    }
}
