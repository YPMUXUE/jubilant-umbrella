package umbrella.producer.pixiv;

import umbrella.bootstrap.InputParameter;
import umbrella.task.ReceiverDTO;

import java.util.concurrent.BlockingQueue;

public class PixivProducerMultiple implements Runnable {
    private BlockingQueue<ReceiverDTO> productQueue;
    private String url;
    private InputParameter parameter;
    private static String sessionKey="PHPSESSID";


    public PixivProducerMultiple(String url, InputParameter parameter, BlockingQueue<ReceiverDTO> productQueue) {
        this.url = url;
        this.productQueue = productQueue;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        try {
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {

        }

    }
}
