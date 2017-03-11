package javabeat.net;

/**
 * Created by THANGTQ on 3/8/2017.
 */

import org.springframework.beans.factory.annotation.Autowired;

public abstract class RequestProcessor {
    @Autowired
    ResourceB resourceB;

    public ResourceB getResourceB() {
        return resourceB;
    }

    public abstract ResourceA getResourceA();
}
