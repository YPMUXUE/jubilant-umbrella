package umbrella.producer;

import umbrella.bootstrap.InputParameter;
import umbrella.task.Receiver;

import java.io.IOException;
import java.net.MalformedURLException;

public interface Start {
    void start(InputParameter parameter) throws IOException, InterruptedException;
    void save(Receiver receiver) throws InterruptedException, MalformedURLException, Exception;
}
