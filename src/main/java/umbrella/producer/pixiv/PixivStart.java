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
import java.net.MalformedURLException;
import java.util.concurrent.*;

public class PixivStart implements Start {
    private static final String host = "https://www.pixiv.net";
    private static final String url = "https://www.pixiv.net/member_illust.php?id=%s&type=all&p=%d";
    private static final String sessionKey = "PHPSESSID";
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
        ExecutorService pageExecutor=Executors.newCachedThreadPool();
        BlockingQueue<Elements> pageQueueDirect=new LinkedBlockingQueue<>();
        BlockingQueue<Elements> pageQueueMultiple=new LinkedBlockingQueue<>();
        Future pageFuture=pageExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    int page = 1;
                    Elements elements = getDocument(parameter, String.format(url, parameter.getUserid(), page))
                            .select("li[class=image-item] .work");
                    //初始页为1，每次循环page+1，直到selector取不到元素
                    while (elements.size()>0) {
                        System.out.println("next page:"+String.format(url, parameter.getUserid(), page));
                        Elements e;
                        if ((e=elements.select("a[class=work  _work multiple]")).size()>0){
                            pageQueueMultiple.put(e);
                        }
                        if ((e=elements.select("a[class=work  _work]")).size()>0){
                            pageQueueDirect.put(e);
                        }
                        page++;
                        //next page
                        elements=getDocument(parameter,String.format(url, parameter.getUserid(), page))
                                .select("li[class=image-item] .work");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println(pageQueueDirect.size());
                    System.out.println(pageQueueMultiple.size());
                }
            }
        });

        CountDownLatch countDownLatch=new CountDownLatch(1);

        Future pageFutureDriect=pageExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Elements elements;
                    while (!pageFuture.isDone() || pageQueueDirect.size()>0 ) {
                        while ((elements = pageQueueDirect.poll(3, TimeUnit.SECONDS)) != null) {
                            for (Element element : elements) {
                                productExecutor.execute(new PixivProducerDirect(addHostName(element.attr("href")), parameter,
                                        productQueue));
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        productExecutor.shutdown();
                    }
                }
            }
        });

        Future pageFutureMultiple=pageExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Elements elements;
                    while (!pageFuture.isDone() || pageQueueMultiple.size()>0 ) {
                        while ((elements = pageQueueMultiple.poll(3, TimeUnit.SECONDS)) != null) {
                            for (Element element : elements) {
                                productExecutor.execute(new PixivProducerMultiple(addHostName(element.attr("href")), parameter,
                                        productQueue));
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            }
        });

    pageExecutor.shutdown();

    }

    @Override
    public void save(Receiver receiver) throws Exception {
        ExecutorService commitExecutor=Executors.newFixedThreadPool(TaskConfig.THREAD_COUNT);
        for (int i=0;i<TaskConfig.THREAD_COUNT;i++){
            commitExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        ReceiverDTO receiverDTO;
                        //productExecutor.isTerminated内所有线程返回时才会为true
                        //可以利用submit产生的future.isDone()
                        while ((!productExecutor.isTerminated()) || productQueue.size() > 0) {
                            while (((receiverDTO = productQueue.poll(3, TimeUnit.SECONDS)) != null)) {
                                receiver.submit(receiverDTO);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{

                    }
                }
            });
        }
        commitExecutor.shutdown();

    }

    private String addHostName(String url) {
        if (url.startsWith("/")) {
            url = host + url;
        }else if (!url.toUpperCase().startsWith("HTTP")){
            url=host+"/"+url;
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
