package saver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

public interface Output {
    void writeToFile(InputStream inputStream, File file) throws IOException;
}
