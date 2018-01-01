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

    public void saveWithRandomName(InputStream inputStream, String suffix) throws IOException {
        output.writeToFile(inputStream, generateFile.generateNewFileWithRandomName(SaverConfig.OUTPUT_PATH, suffix));
    }
    public void save(InputStream inputStream,String fileName,String suffix) throws IOException {
        output.writeToFile(inputStream, generateFile.generateNewFile(SaverConfig.OUTPUT_PATH, fileName,suffix));
    }

    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();
        String url = "https://i.pximg.net/img-master/img/2017/12/27/00/00/10/66474802_p0_master1200.jpg";
        URLConnection urlConnection = (new URL(url)).openConnection();
        urlConnection.setRequestProperty("referer","https://www.pixiv.net/member_illust.php?mode=manga&illust_id=66474802");
        urlConnection.connect();
        Long mid = System.currentTimeMillis();
            ReadyToSave readyToSave = new ReadyToSave();
            readyToSave.setOutput(new OutputByChannel());
            readyToSave.save(urlConnection.getInputStream(),"test",
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
