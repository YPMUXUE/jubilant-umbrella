package umbrella.producer.pixiv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import umbrella.bootstrap.InputParameter;
import umbrella.connection.proxy.ProxyFactory;
import umbrella.producer.Producter;
import umbrella.task.ReceiverDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class PixivProducerDirect implements Producter {

    private BlockingQueue<ReceiverDTO> productQueue;
    private String url;
    private InputParameter parameter;
    private static String sessionKey="PHPSESSID";
    private static String selector="img[class=original-image]";


    public PixivProducerDirect(String url, InputParameter parameter, BlockingQueue<ReceiverDTO> productQueue) {
        this.url = url;
        this.productQueue = productQueue;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        try {
            Document document=getDocument(parameter,url);
            Elements elements=document.select(selector);
            ReceiverDTO receiverDTO;
            for (Element element:elements){
                String src=element.attr("data-src");
                receiverDTO=new ReceiverDTO();
                Map<String,String> map=new HashMap<>(3);
                map.put("referer",url);
                receiverDTO.setRequestPropertyAdd(map);
                receiverDTO.setFileName(src.substring(src.lastIndexOf("/img/")+5,src.length()).replace("/",""));
                receiverDTO.setUrl(src);
                productQueue.put(receiverDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {

        }

    }
    private Document getDocument(InputParameter parameter, String url) throws IOException {
        Document document;
        if (parameter.isProxy()) {
            document = Jsoup.connect(url).proxy(ProxyFactory.getProxy())
                    .cookie(sessionKey, parameter.getSessionValue()).get();
        } else {
            document = Jsoup.connect(url)
                    .cookie(sessionKey, parameter.getSessionValue()).get();
        }
        return document;
    }
}
