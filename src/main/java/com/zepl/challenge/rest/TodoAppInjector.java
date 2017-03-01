package com.zepl.challenge.rest;
import com.google.inject.AbstractModule;

/**
 * Created by THANGTQ.
 */
public class TodoAppInjector extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        bind(Service.class).to(ToDoService.class);
    }
}
