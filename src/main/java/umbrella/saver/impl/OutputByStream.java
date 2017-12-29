package umbrella.saver.impl;

import umbrella.saver.Output;
import umbrella.saver.config.SaverConfig;

import java.io.*;

public class OutputByStream implements Output {
    @Override
    public void writeToFile(InputStream inputStream, File file) throws IOException {
        try (
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                BufferedInputStream in = new BufferedInputStream(inputStream)
        ) {
            byte[] bytes = new byte[SaverConfig.READ_WRITE_BUFFERED_SIZE];
            int len;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
        }

    }
}
