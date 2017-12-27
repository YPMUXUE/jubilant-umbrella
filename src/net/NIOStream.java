package net;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOStream {
    private static String filePath = "C:\\Users\\yp\\Desktop\\2.jpg";
    private static String url = "http://d.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40fe871b36bad64034f79f019d4.jpg";
    private static int timeout = 180000;

    public static void main(String[] args) throws Exception {
        InputStream netIs = null;
        Long startTime = System.currentTimeMillis();
        try {
            URLConnection urlConnection = (new URL(url)).openConnection();
            urlConnection.setConnectTimeout(timeout);
            netIs = urlConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(netIs ,Charset.forName("UTF-8")));
//            while (bufferedReader.ready()) {
//                System.out.println(bufferedReader.readLine());
//            }
            BufferedInputStream bufferedInputStream = new BufferedInputStream(netIs);
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileChannel fo=new RandomAccessFile(file,"rw").getChannel();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            byte[] bytes=new byte[1024];
            int len;
            while((len=dataInputStream.read(bytes))!=-1) {
                byteBuffer.clear();
                byteBuffer.put(bytes,0,len);
                byteBuffer.flip();
                fo.write(byteBuffer);
            }
            fo.close();
        } finally {
            if (netIs != null) {
                netIs.close();
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }
}
