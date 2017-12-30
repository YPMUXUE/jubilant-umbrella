package umbrella.saver.config;

public class SaverConfig {
    public static int READ_WRITE_BUFFERED_SIZE=10240;
    public static String OUTPUT_PATH = "C:\\Users\\yp\\Desktop";

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();

        stringBuilder.append("SaverConfig-->");
        stringBuilder.append("READ_WRITE_BUFFERED_SIZE:");
        stringBuilder.append(READ_WRITE_BUFFERED_SIZE);
        stringBuilder.append(",");
        stringBuilder.append("OUTPUT_PATH:");
        stringBuilder.append(OUTPUT_PATH);
        stringBuilder.append(",");
        return stringBuilder.toString();
    }
}
