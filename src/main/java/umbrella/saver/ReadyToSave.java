package umbrella.saver;

import umbrella.saver.config.SaverConfig;
import umbrella.saver.impl.GenerateFileImpl;
import umbrella.saver.impl.*;

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

    public void save(InputStream inputStream, String suffix) throws IOException {
        output.writeToFile(inputStream, generateFile.generateNewFileWithRandomName(SaverConfig.OUTPUT_PATH, suffix));
    }

    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();
        String url = "http://image.tianjimedia.com/uploadImages/2013/030/DKTWSM335I06.jpg";
        URLConnection urlConnection = (new URL(url)).openConnection();
        urlConnection.connect();
        Long mid = System.currentTimeMillis();
            ReadyToSave readyToSave = new ReadyToSave();
            readyToSave.setOutput(new OutputByChannel());
            readyToSave.save(urlConnection.getInputStream(),
                    url.substring(url.lastIndexOf(".")));

        System.out.println("start" + (System.currentTimeMillis() - start));
        System.out.println("mid" + (System.currentTimeMillis() - mid));
    }

    public void setGenerateFile(GenerateFile generateFile) {
        this.generateFile = generateFile;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
