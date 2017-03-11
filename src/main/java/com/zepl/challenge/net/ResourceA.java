/**
 * Created by THANGTQ on 3/8/2017.
 */
package com.zepl.challenge.net;

public class ResourceA {
    String url = "http://localhost:8080";

    public ResourceA() {
        System.out.println("Resource A instance creation");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
