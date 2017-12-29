package umbrella.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws Exception{
        ServerSocket server=new ServerSocket(8080);
        Socket s=server.accept();
        BufferedReader reader=new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println(reader.readLine());
        while (reader.ready()){
            System.out.println(reader.readLine());
        }
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bufferedWriter.write("HTTP/1.1 200 OK");
        bufferedWriter.flush();
       // System.out.println(reader.readLine());

        bufferedWriter.flush();
    }
}
