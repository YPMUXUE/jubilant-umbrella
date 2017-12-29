package umbrella.saver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Output {
    void writeToFile(InputStream inputStream, File file) throws IOException;
}
