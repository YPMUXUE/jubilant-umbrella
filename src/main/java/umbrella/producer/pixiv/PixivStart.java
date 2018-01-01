package umbrella.producer.pixiv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import umbrella.bootstrap.InputParameter;
import umbrella.connection.proxy.ProxyFactory;
import umbrella.producer.Start;
import umbrella.task.Receiver;
import umbrella.task.ReceiverDTO;
import umbrella.task.config.TaskConfig;

import java.io.IOException;
import java.util.concurrent.*;

public class PixivStart implements Start {
    private static final String host = "https://www.pixiv.net";
    private static final String url = "https://www.pixiv.net/member_illust.php?id=%s";
    private static final String sessionKey = "PHPSESSID";
    private static final String selectorMultiple = "li[class=image-item]>a[class=work  _work multiple]";
    private static final String selector = "li[class=image-item]>a[class=work  _work]";
    private ExecutorService productExecutor;
    private BlockingQueue<ReceiverDTO> productQueue;

    public PixivStart(ExecutorService productExecutor, BlockingQueue<ReceiverDTO> productQueue) {
        this.productExecutor = productExecutor;
        this.productQueue = productQueue;
    }

    public PixivStart() {
        this(Executors.newFixedThreadPool(TaskConfig.THREAD_COUNT), new LinkedBlockingQueue<>());
    }

    @Override
    public void start(InputParameter parameter) throws IOException {
        String newUrl = String.format(url, parameter.getUserid());
        Document document = getDocument(parameter, newUrl);
        Long start = System.currentTimeMillis();
        productExecutor = Executors.newFixedThreadPool(TaskConfig.THREAD_COUNT);
        Elements elements = document.select(selector);
        for (Element element : elements) {
            productExecutor.execute(new PixivProducerDirect(addHostName(element.attr("href")),parameter,
                    productQueue));
        }
        elements = document.select(selectorMultiple);
        for (Element element : elements) {
            productExecutor.execute(new PixivProducerMultiple(addHostName(element.attr("href")),parameter,
                    productQueue));
        }
        productExecutor.shutdown();


        System.out.println(System.currentTimeMillis() - start);
    }

    @Override
    public void save(Receiver receiver) throws InterruptedException {
        ReceiverDTO receiverDTO = null;
        //productExecutor.isTerminated内所有线程返回时才会为true
        while (productQueue.size()>0 || (!productExecutor.isTerminated())){
            while (((receiverDTO=productQueue.poll(3, TimeUnit.SECONDS))!=null)){
                receiver.submit(receiverDTO);
            }
        }

    }

    private String addHostName(String url) {
        if (url.startsWith("/")) {
            url = host + url;
        }
        return url;
    }

    public Document getDocument(InputParameter parameter, String url) throws IOException {
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
