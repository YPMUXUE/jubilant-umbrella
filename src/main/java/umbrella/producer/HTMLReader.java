package umbrella.producer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import umbrella.connection.proxy.ProxyFactory;

public class HTMLReader {
    public static void main(String[] args) throws Exception{
        String URL="https://www.pixiv.net/member_illust.php?mode=medium&illust_id=%s";
        String sessionId="28310717_7c5889d8e720665360d1bbc37486e142";
        Document document= Jsoup.connect(URL).proxy(ProxyFactory.getProxy()).cookie("PHPSESSID",sessionId).get();
        System.out.println(document.select("div[class=works_display]>a[class=_work multiple]"));


    }
}
