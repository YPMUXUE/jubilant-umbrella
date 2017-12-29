package saver.impl;

import config.StaticConfig;
import saver.Output;

import java.io.*;

public class OutputByStream implements Output {
    @Override
    public void writeToFile(InputStream inputStream, File file) throws IOException {
        DataOutputStream out = null;
        BufferedInputStream in = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            in = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[StaticConfig.READ_WRITE_BUFFERED_SIZE];
            int len;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }
}
