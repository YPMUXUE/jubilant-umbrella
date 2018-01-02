package umbrella.bootstrap;

public class InputParameter {
    private String userid;
    private String sessionKey;
    private String sessionValue;
    private boolean proxy;

    public InputParameter(){}

     void setUserid(String userid) {
        this.userid = userid;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

     void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }

     void setProxy(String proxy) {
        this.proxy = Boolean.valueOf(proxy);
    }

    public boolean isProxy() {
        return proxy;
    }


    public String getUserid() {
        return userid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getSessionValue() {
        return sessionValue;
    }
}
