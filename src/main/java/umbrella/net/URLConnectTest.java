package umbrella.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectTest {
    public static void main(String[] args) throws Exception{
        URL url=new URL("localhost");
        Proxy proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",1080));
        URLConnection connection=url.openConnection();
        connection.connect();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        System.out.println(bufferedReader.readLine());
        while (bufferedReader.ready()){
            System.out.println(bufferedReader.readLine());
        }

    }
}
