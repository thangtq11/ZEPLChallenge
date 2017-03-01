package com.zepl.challenge.rest;
import com.google.inject.Inject;
import com.zepl.challenge.data.Entity;
import com.zepl.challenge.data.Task;
import com.zepl.challenge.data.TaskType;
import java.util.List;



/**
 * Todo application for creating, updating,delete service
 */
public class ToDoApplication {
    private ToDoService service;
    //setter method injector for guice
    @Inject
    public void setService(ToDoService svc){
        this.service=svc;
    }
    public Entity create(String name){
         return service.create(name);
    }
    public Entity createTask(String idTodo,Task task){
        return service.createTask(idTodo,task);
    }
    public Entity updateTask(String idTodo,Task task){
        return service.updateTask(idTodo,task);
    }
    public boolean deleteTask(String idTodo, String idTask)    {return  service.deleteTask(idTodo,  idTask);}
    public Entity getTodoTask(String idTodo, String idTask)    {return  service.getTodoTask(idTodo,  idTask);}
    public boolean deleteTodoAndRelatedTasks(String idTodo)    {return  service.deleteTodoAndRelatedTasks(idTodo);}
    public List getTodos()
    {
        return service.getTodos();
    }
    public List getTodoTasks(String idTodo, TaskType tasktype) {return service.getTodoTasks(idTodo,tasktype);}
}
