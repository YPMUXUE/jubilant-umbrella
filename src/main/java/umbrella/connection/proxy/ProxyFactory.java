package umbrella.connection.proxy;

import umbrella.connection.proxy.config.ProxyConfig;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyFactory {
    private static Proxy proxy = new Proxy(Proxy.Type.valueOf(ProxyConfig.PROXY_TYPE),
            new InetSocketAddress(ProxyConfig.PROXY_IP_ADDRESS, ProxyConfig.PROXY_PORT));

    private ProxyFactory() {

    }

    public static Proxy getProxy() {
        return proxy;
    }
}

