package umbrella.saver.impl;

import umbrella.saver.Output;
import umbrella.saver.config.SaverConfig;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class OutputByChannel implements Output {
    @Override
    public void writeToFile(InputStream inputStream, File file) throws IOException {

        try (
                ReadableByteChannel read = Channels.newChannel(new BufferedInputStream(inputStream));
                FileChannel write = new RandomAccessFile(file, "rw").getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(SaverConfig.READ_WRITE_BUFFERED_SIZE);
            while (read.read(buffer) != -1) {
                buffer.flip();
                write.write(buffer);
                buffer.clear();
            }
        }
    }
}
