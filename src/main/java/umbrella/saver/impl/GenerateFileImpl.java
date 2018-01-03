package umbrella.saver.impl;

import umbrella.saver.GenerateFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GenerateFileImpl implements GenerateFile {
    private static final String POINT = ".";

    @Override
    public boolean generatePath(String path) {
        File file = new File(path);
        boolean createIsSuccess;
        if (file.isDirectory()) {
            if (!file.exists()) {
                createIsSuccess = file.mkdirs();
            } else {
                return true;
            }
        } else {
            if (!file.getParentFile().exists()) {
                createIsSuccess = file.getParentFile().mkdirs();
            } else {
                return true;
            }
        }
        return createIsSuccess;
    }

    @Override
    public File generateNewFile(String filePath, String fileName, String suffix) throws IOException {
        if (!suffix.startsWith(POINT)) {
            suffix = POINT + suffix;
        }
        if (!filePath.endsWith("\\")){
            filePath=filePath+"\\";
        }
        String fileStr = filePath + fileName + suffix;
        generatePath(fileStr);
        File file = new File(fileStr);
        if (file.exists()) {
            do{
                fileName=fileName+System.currentTimeMillis();
                file=new File(filePath + fileName + suffix);
            }while (file.exists());
            file.createNewFile();
            return file;
        } else {
            file.createNewFile();
            return file;
        }

    }

    public File generateNewFile(String filePath, String fileName) throws IOException {
        if (!filePath.endsWith("\\")){
            filePath=filePath+"\\";
        }
        String fileStr = filePath + fileName;
        generatePath(fileStr);
        File file = new File(fileStr);
        if (file.exists()) {
            do{
                fileName=fileName+System.currentTimeMillis();
                file=new File(filePath + fileName);
            }while (file.exists());
            file.createNewFile();
            return file;
        } else {
            file.createNewFile();
            return file;
        }

    }

    @Override
    public File generateNewFileWithRandomName(String filePath, String suffix) throws IOException {
        if (!suffix.startsWith(POINT)) {
            suffix = POINT + suffix;
        }
        if (!filePath.endsWith("\\")){
            filePath=filePath+"\\";
        }
        UUID uuid = UUID.randomUUID();
        String fileStr = filePath + uuid.toString() + suffix;
        generatePath(fileStr);
        File file = new File(fileStr);
        if (file.exists()) {
            return file;
        } else {
            file.createNewFile();
            return file;
        }
    }
}
