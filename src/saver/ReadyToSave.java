package saver;

import config.StaticConfig;
import saver.impl.GenerateFileImpl;
import saver.impl.OutputByStream;
import sun.swing.FilePane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ReadyToSave {
    GenerateFile generateFile;

    Output output;

    public ReadyToSave(GenerateFile generateFile, Output output) {
        this.generateFile = generateFile;
        this.output = output;
    }
    public ReadyToSave() {
        this.generateFile = new GenerateFileImpl();
        this.output = new OutputByStream();
    }

    public void save(InputStream inputStream,String filePath,String suffix)throws IOException{
        output.writeToFile(inputStream,generateFile.generateNewFileWithRandomName(filePath,suffix));
    }

    public static void main(String[] args) throws Exception{
        Long start=System.currentTimeMillis();
        String url="https://pic4.zhimg.com/v2-16bf98dd9d8c6a5a89b574156e5f271e_xl.jpg";
        URLConnection urlConnection = (new URL(url)).openConnection();
        urlConnection.connect();
        Long mid=System.currentTimeMillis();
        new ReadyToSave().save( urlConnection.getInputStream(),
                StaticConfig.OUTPUT_PATH,
                url.substring(url.lastIndexOf(".")));
        System.out.println("start"+(System.currentTimeMillis()-start));
        System.out.println("mid"+(System.currentTimeMillis()-mid));
    }
}
