package umbrella.task;

import java.util.Map;

public class ReceiverDTO {
    private String url;
    private Map<String,String> requestPropertySet;
    private Map<String,String> requestPropertyAdd;
    private String postData;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getRequestPropertySet() {
        return requestPropertySet;
    }

    public void setRequestPropertySet(Map<String, String> requestPropertySet) {
        this.requestPropertySet = requestPropertySet;
    }

    public Map<String, String> getRequestPropertyAdd() {
        return requestPropertyAdd;
    }

    public void setRequestPropertyAdd(Map<String, String> requestPropertyAdd) {
        this.requestPropertyAdd = requestPropertyAdd;
    }

    public String getPostData() {
        return postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }
}
