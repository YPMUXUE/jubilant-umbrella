package umbrella.connection.proxy.config;

public class ProxyConfig {
    public static int PROXY_PORT=1080;
    public static String PROXY_IP_ADDRESS="127.0.0.1";
    public static String PROXY_TYPE="HTTP";

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();

        stringBuilder.append("ProxyConfig-->");
        stringBuilder.append("PROXY_PORT:");
        stringBuilder.append(PROXY_PORT);
        stringBuilder.append(",");
        stringBuilder.append("PROXY_IP_ADDRESS:");
        stringBuilder.append(PROXY_IP_ADDRESS);
        stringBuilder.append(",");
        stringBuilder.append("PROXY_TYPE:");
        stringBuilder.append(PROXY_TYPE);
        return stringBuilder.toString();

    }
}
