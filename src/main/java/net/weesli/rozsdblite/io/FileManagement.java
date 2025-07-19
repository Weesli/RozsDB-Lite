package net.weesli.rozsdblite.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileManagement {
    public byte[] readDatabaseFile(Path path) {
        if (!path.toFile().exists()) {
            return null;
        }
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            byte[] data = new byte[byteBuffer.remaining()];
            byteBuffer.get(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeDatabaseFile(Path path, byte[] data) {
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            fileChannel.write(ByteBuffer.wrap(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
