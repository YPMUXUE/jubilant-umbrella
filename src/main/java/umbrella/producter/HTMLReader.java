package umbrella.producter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import umbrella.connection.proxy.ProxyFactory;

public class HTMLReader {
    public static void main(String[] args) throws Exception{
        String userid="122233";
        String beforeURL="https://www.pixiv.net/member_illust.php?id=%s";
        String sessionId="28310717_77076bdfc207988a8c93dc28a1aecfdd";
        String afterUrl=String.format(beforeURL,userid);
        Document document= Jsoup.connect(afterUrl).proxy(ProxyFactory.getProxy()).cookie("PHPSESSID",sessionId).get();
//.work:not(.multiple)
        System.out.println(document.select("a[class=work  _work multiple]"));


    }
}
