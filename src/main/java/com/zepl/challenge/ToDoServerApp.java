package com.zepl.challenge;

import com.zepl.challenge.rest.ToDoRestController;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
/**
 * simplify registration of rest and components using class-scanning.
 *
 */
@Component
public class ToDoServerApp extends ResourceConfig {
    public ToDoServerApp() {
        register(JacksonFeature.class);
        register(ToDoRestController.class);
    }
}
