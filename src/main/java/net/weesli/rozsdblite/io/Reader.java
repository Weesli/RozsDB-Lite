package net.weesli.rozsdblite.io;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import net.weesli.rozsdblite.model.DatabaseImpl;
import net.weesli.rozsdblite.util.CompressUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Reader {

    private static final DslJson<Object> DSL_JSON = new DslJson<>();

    private final DatabaseImpl parent;
    private final FileManagement fileManagement;

    public Reader(DatabaseImpl parent, FileManagement fileManagement) {
        this.parent = parent;
        this.fileManagement = fileManagement;
    }

    public HashMap<String, LinkedHashMap<String, String>> read() throws Exception {
        Path dbPath = parent.getDatabasePath();

        byte[] compressed = readFileInParallel(dbPath);

        HashMap<String, LinkedHashMap<String, String>> result = new HashMap<>();
        if (compressed.length == 0) {
            return result;
        }

        byte[] data = CompressUtil.decompress(compressed);
        if (data.length == 0) {
            return result;
        }

        try {
            JsonReader<Object> reader = DSL_JSON.newReader(data).process(data, data.length);
            HashMap<String, LinkedHashMap<String, String>> parsed = reader.next(HashMap.class);
            if (parsed != null) {
                result.putAll(parsed);
            }
        } catch (IOException e) {
            throw new RuntimeException("DB JSON parse error", e);
        }

        return result;
    }
    public static byte[] readFileInParallel(Path path) throws Exception {
        long fileSize = Files.size(path);
        if (fileSize == 0) {
            return new byte[0];
        }
        final long MIN_CHUNK = 256 * 1024;
        int suggested = (int) Math.min(10, Math.max(1, (fileSize + MIN_CHUNK - 1) / MIN_CHUNK));
        return readFileInParallel(path, suggested);
    }

    public static byte[] readFileInParallel(Path path, int numParts) throws Exception {
        long fileSize = Files.size(path);
        if (fileSize == 0) {
            return new byte[0];
        }
        if (fileSize > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("File too large to load into single byte[] (>2GB): " + fileSize);
        }

        if (numParts <= 0) {
            numParts = 1;
        } else if (numParts > 256) {
            numParts = 256;
        }

        long chunkSize = (fileSize + numParts - 1) / numParts;

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            List<CompletableFuture<Chunk>> futures = new ArrayList<>(numParts);

            for (int i = 0; i < numParts; i++) {
                long start = i * chunkSize;
                if (start >= fileSize) break;
                long size = Math.min(chunkSize, fileSize - start);
                futures.add(readChunkAsync(channel, i, start, (int) size));
            }

            byte[] result = new byte[(int) fileSize];

            for (CompletableFuture<Chunk> f : futures) {
                Chunk c = f.get();
                System.arraycopy(c.data, 0, result, (int) c.start, c.data.length);
            }

            return result;
        }
    }

    private static CompletableFuture<Chunk> readChunkAsync(
            AsynchronousFileChannel channel, int index, long position, int size) {

        ByteBuffer buffer = ByteBuffer.allocate(size);
        CompletableFuture<Chunk> future = new CompletableFuture<>();

        channel.read(buffer, position, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer bytesRead, Object attachment) {
                if (bytesRead == -1) {
                    future.completeExceptionally(
                            new IOException("EOF reached unexpectedly at position " + position));
                    return;
                }
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                future.complete(new Chunk(index, position, data));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                future.completeExceptionally(exc);
            }
        });

        return future;
    }

    private static class Chunk {
        final int index;
        final long start;
        final byte[] data;

        Chunk(int index, long start, byte[] data) {
            this.index = index;
            this.start = start;
            this.data = data;
        }
    }
}
