package com.zepl.challenge.rest;

import com.zepl.challenge.dao.ToDoDao;
import com.zepl.challenge.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.UUID;

/**
 * Implement a todo service
 * Created by THANGTQ
 */
public class ToDoService implements Service{
    private final static Logger log = LoggerFactory.getLogger(ToDoService.class);
    public Entity create(String name)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            ToDo todo = ImmutableToDo.builder().id(UUID.randomUUID().toString()).name(name).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
            return (toDoDao.insertToDo(todo)==true?todo:null);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }
    public Entity createTask(String idToDo, Task task)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return (toDoDao.insertTodoTask(idToDo,task)==true?task:null);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }
    public Entity updateTask(String idToDo, Task task)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return (toDoDao.updateTask(idToDo,task)==true?task:null);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }
    public boolean deleteTask(String idToDo, String  idTask)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return toDoDao.deleteTask(idToDo,idTask);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return false;
    }
    public boolean deleteTodoAndRelatedTasks(String idToDo)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return toDoDao.deleteTodoAndRelatedTasks(idToDo);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return false;
    }



    public List getTodos()
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return toDoDao.getToDos();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }
    public List getTodoTasks(String idTodo,TaskType tasktype)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return toDoDao.getTodoTasksByStatus(idTodo,tasktype);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }
    public Task getTodoTask(String idTodo, String idTask)
    {
        try {
            ToDoDao toDoDao = ToDoDao.getInstance();
            return toDoDao.getTodoTask(idTodo,idTask);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return null;
    }


}
