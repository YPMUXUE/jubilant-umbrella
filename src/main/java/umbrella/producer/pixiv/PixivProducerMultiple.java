package umbrella.producer.pixiv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import umbrella.bootstrap.InputParameter;
import umbrella.connection.proxy.ProxyFactory;
import umbrella.task.ReceiverDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class PixivProducerMultiple implements Runnable {
    private BlockingQueue<ReceiverDTO> productQueue;
    private String url;
    private InputParameter parameter;
    private static String sessionKey="PHPSESSID";
    private static String selectorFirst="div[class=works_display]>a[class=_work multiple]";
    private static final String host = "https://www.pixiv.net";


    public PixivProducerMultiple(String url, InputParameter parameter, BlockingQueue<ReceiverDTO> productQueue) {
        this.url = url;
        this.productQueue = productQueue;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        try {
            try {
                ReceiverDTO receiverDTO;
                Document document=getDocument(parameter,url);
                Elements elements=document.select(selectorFirst);
                for (Element element:elements){
                    String src=addHostName(element.attr("href"));
                    Document secondDoc=getDocument(parameter,src);
                    Elements secondElements=secondDoc.select("div[class=item-container]>a");
                    for(Element secondElement:secondElements){
                        String href=addHostName(secondElement.attr("href"));
                        Document thirdDoc=getDocument(parameter,href);
                        Elements thirdElements=thirdDoc.select("img");
                        for (Element thirdElement:thirdElements){
                            String imgSrc=thirdElement.attr("src");
                            receiverDTO=new ReceiverDTO();
                            receiverDTO.setFileName(imgSrc.substring(imgSrc.lastIndexOf("/img/")+5,imgSrc.length()).replace("/",""));
                            receiverDTO.setUrl(imgSrc);
                            Map<String,String> map=new HashMap<>(3);
                            map.put("referer",href);
                            receiverDTO.setRequestPropertyAdd(map);
                            productQueue.put(receiverDTO);
                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } finally {

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
    private String addHostName(String url) {
        if (url.startsWith("/")) {
            url = host + url;
        }else if (!url.toUpperCase().startsWith("HTTP")){
            url=host+"/"+url;
        }
        return url;
    }
}
