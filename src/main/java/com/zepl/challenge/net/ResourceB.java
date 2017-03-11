package javabeat.net;

/**
 * Created by THANGTQ on 3/8/2017.
 */

public class ResourceB {
    String url = "http://localhost:8081";

    public ResourceB() {
        System.out.println("Resource B instance creation");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
