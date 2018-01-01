package umbrella.task;

import java.io.InputStream;

public interface Receiver {
    void submit(InputStream inputStream);
    void submit(ReceiverDTO dto);
}
