package umbrella.task;

import java.io.InputStream;
import java.net.MalformedURLException;

public interface Receiver {
    void submit(InputStream inputStream);
    void submit(ReceiverDTO dto) throws MalformedURLException, Exception;
}
