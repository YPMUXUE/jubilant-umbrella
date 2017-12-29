package net;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class NetIOStream {
    private static String filePath = "C:\\Users\\yp\\Desktop\\1.jpg";
    private static String url = "http://d.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40fe871b36bad64034f79f019d4.jpg";
    private static int timeout = 180000;

    public static void main(String[] args) throws Exception {
        InputStream netIs = null;
        DataOutputStream dataOutputStream = null;
        Long startTime = 0L;
        try {
            URLConnection urlConnection = (new URL(url)).openConnection();
            urlConnection.setConnectTimeout(timeout);
            netIs = urlConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(netIs ,Charset.forName("UTF-8")));
//            while (bufferedReader.ready()) {
//                System.out.println(bufferedReader.readLine());
//            }
            startTime = System.currentTimeMillis();
           // for(int i=0;i<10;i++) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(netIs);
                DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
                File file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                byte[] bytes = new byte[1024];
                int len;
                while ((len = dataInputStream.read(bytes)) != -1) {
                    dataOutputStream.write(bytes, 0, len);
                }
                dataOutputStream.flush();
            //}
        } finally {
            if (netIs != null) {
                netIs.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }
}
