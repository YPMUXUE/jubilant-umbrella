package umbrella.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketTest {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("localhost",8080);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("POST /SockTestServlet HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9,zh-TW;q=0.8\n" +
                "Cookie: JSESSIONID=D0804A9B1358B4C4B949C4E42BBC6FBA\n" +
                "Content-Length: 314\n" +
                "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                "\n");
        writer.write("lowpriceCond={\"tripType\":\"OW\",\"adtCount\":1,\"chdCount\":0,\"infCount\":1,\"currency\":\"CNY\",\"sortType\":\"a\",\"segmentList\":[{\"deptCd\":\"XIY\",\"arrCd\":\"LAX\",\"deptDt\":\"2018-01-20\",\"deptAirport\":\"\",\"arrAirport\":\"\",\"deptCdTxt\":\"西安\",\"arrCdTxt\":\"洛杉矶\",\"deptCityCode\":\"SIA\",\"arrCityCode\":\"LAX\"}],\"sortExec\":\"a\",\"page\":\"0\"}");
        writer.flush();
        InputStream inputStream=socket.getInputStream();
        //BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
//        System.out.println(reader.readLine());
        int count=0;
        List<Byte> list=new ArrayList<>();
        byte bytes;
        Long startTime=System.currentTimeMillis();
        while ((bytes=(byte)inputStream.read())!=-1){
            list.add(bytes);
        }
        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(list);
//        while(reader.ready()){
//            String read=reader.readLine();
//            System.out.print(read);
//            count++;
//        }
        socket.close();
    }
}
