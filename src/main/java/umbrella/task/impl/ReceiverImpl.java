package umbrella.task.impl;

import org.apache.commons.lang3.StringUtils;
import umbrella.bootstrap.ParameterFactory;
import umbrella.connection.Connections;
import umbrella.saver.ReadyToSave;
import umbrella.task.Receiver;
import umbrella.task.ReceiverDTO;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ReceiverImpl implements Receiver {
    ReadyToSave readyToSave;

    public ReceiverImpl(ReadyToSave readyToSave) {
        this.readyToSave = readyToSave;
    }

    public ReceiverImpl() {
        this(new ReadyToSave());
    }


    @Override
    public void submit(InputStream inputStream) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void submit(ReceiverDTO receiverDTO) throws Exception {
        BufferedWriter writer=null;
        try {
            System.out.println(receiverDTO.getFileName());
            System.out.println(receiverDTO.getUrl());
            URLConnection connection = Connections
                    .getConnection(new URL(receiverDTO.getUrl()), ParameterFactory.getInputParameter().isProxy());
            Map<String, String> mapAdd = receiverDTO.getRequestPropertyAdd();
            if (mapAdd != null) {
                for (Map.Entry<String, String> entry : mapAdd.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            Map<String, String> mapSet = receiverDTO.getRequestPropertySet();
            if (mapSet != null) {
                for (Map.Entry<String, String> entry : mapSet.entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    connection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (!StringUtils.isEmpty(receiverDTO.getPostData())) {
                connection.setDoOutput(true);
                writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(receiverDTO.getPostData());
                writer.flush();
            }
            readyToSave.save(connection.getInputStream(),receiverDTO.getFileName());
        }finally{
            if (writer!=null) {
                writer.close();
            }
        }
    }
}
