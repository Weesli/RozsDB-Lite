package net.weesli.rozsdblite.util;

import io.airlift.compress.zstd.ZstdCompressor;
import io.airlift.compress.zstd.ZstdDecompressor;

import java.nio.ByteBuffer;

public class CompressUtil {
    private static final ZstdCompressor COMPRESSOR = new ZstdCompressor();
    private static final ZstdDecompressor DECOMPRESSOR = new ZstdDecompressor();

    public static byte[] compress(byte[] data) {
        byte[] compressed = new byte[COMPRESSOR.maxCompressedLength(data.length)];
        int compressedSize = COMPRESSOR.compress(data, 0, data.length, compressed, 0, compressed.length);

        ByteBuffer buffer = ByteBuffer.allocate(4 + compressedSize);
        buffer.putInt(data.length);
        buffer.put(compressed, 0, compressedSize);
        return buffer.array();
    }

    public static byte[] decompress(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int originalLength = buffer.getInt();
        byte[] decompressed = new byte[originalLength];
        DECOMPRESSOR.decompress(
                data, 4, data.length - 4,
                decompressed, 0, originalLength
        );
        return decompressed;
    }
}
