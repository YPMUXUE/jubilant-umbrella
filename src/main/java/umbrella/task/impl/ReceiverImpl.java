package umbrella.task.impl;

import umbrella.task.Receiver;
import umbrella.task.ReceiverDTO;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ReceiverImpl implements Receiver {
    private ExecutorService executorService;

    @Override
    public void submit(InputStream inputStream) {

    }

    @Override
    public void submit(ReceiverDTO receiverDTO) {
        System.out.println(receiverDTO.getFileName());
        Map<String,String> map=receiverDTO.getRequestPropertyAdd();
        for (Map.Entry entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
