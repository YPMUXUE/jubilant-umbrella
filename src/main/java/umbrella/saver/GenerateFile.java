package umbrella.saver;

import java.io.File;
import java.io.IOException;

public interface GenerateFile {
    boolean generatePath(String path);

    File generateNewFile(String filePath, String fileName, String suffix) throws IOException;

    File generateNewFileWithRandomName(String filePath, String suffix) throws IOException;
}
