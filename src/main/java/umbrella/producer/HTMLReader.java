package umbrella.producer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import umbrella.connection.proxy.ProxyFactory;

public class HTMLReader {
    public static void main(String[] args) throws Exception{
        String URL="https://www.pixiv.net/member_illust.php?id=490219&type=all&p=4";
        String sessionId="28310717_77076bdfc207988a8c93dc28a1aecfdd";
        Document document= Jsoup.connect(URL).proxy(ProxyFactory.getProxy()).cookie("PHPSESSID",sessionId).get();
        Elements elements=document.select("li[class=image-item] .work");
        System.out.println(elements.select("a[class=work  _work]"));



    }
}
