package umbrella.connection;

import umbrella.connection.proxy.ProxyFactory;
import umbrella.saver.ReadyToSave;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Connections {

//    public static InputStream getInputStream(URL url) throws IOException {
//        return getInputStream(url, false);
//    }
//
//    public static InputStream getInputStream(String url) throws IOException {
//        return getInputStream(url, false);
//    }

    public static InputStream getInputStream(URL url, boolean isProxy) throws IOException {
        if (isProxy) {
            return getInputStream(url.openConnection(ProxyFactory.getProxy()));
        } else {
            return getInputStream(url.openConnection());
        }
    }

    public static InputStream getInputStream(String url, boolean isProxy) throws IOException {
        if (isProxy) {
            return getInputStream(new URL(url).openConnection(ProxyFactory.getProxy()));
        } else {
            return getInputStream(new URL(url).openConnection());
        }
    }

    private static InputStream getInputStream(URLConnection urlConnection) throws IOException {
        return urlConnection.getInputStream();
    }

    public static void main(String[] args) throws Exception{
        String url="http://d.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40fe871b36bad64034f79f019d4.jpg";
        ReadyToSave readyToSave=new ReadyToSave();
        readyToSave.save(Connections.getInputStream(url,true),url.substring(url.lastIndexOf(".")));
    }
}