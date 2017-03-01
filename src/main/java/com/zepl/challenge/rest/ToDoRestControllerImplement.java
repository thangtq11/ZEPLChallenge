package com.zepl.challenge.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zepl.challenge.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * Implement real functions of the ToDoRestController
 */
@Controller
public class ToDoRestControllerImplement {

    private final static Logger log = LoggerFactory.getLogger(ToDoRestControllerImplement.class);

    @Inject
    private Gson gson;

    private ToDoApplication todoApp;

    @Inject
    public ToDoRestControllerImplement() {
        Injector injector = Guice.createInjector(new TodoAppInjector());
        todoApp = injector.getInstance(ToDoApplication.class);
    }
    /**
     * Create a new todo by name in json format. Identity of the todo is auto generated, Created time is taken from system current time.
     * @param toDoNameJson name of the todo in json format
     *                     {
     *                     "name": "Name of the todo"
     *                     }
     * @return HTTP Response for the create operation
     * Payload will look like this
     * <p>
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "Todo name",
     * "created": "2016-12-07 05:42:29.809"
     * }
     */
    public Response createToDo(String toDoNameJson) {
        try {
            log.debug("createToDo({})", toDoNameJson);
            Map<String, Object> map = gson.fromJson(toDoNameJson, new TypeToken<Map<String, String>>() {
            }.getType());
            Entity todo = todoApp.create(map.get(EntityType.name.name()).toString());
            log.debug("create a ToDo from gson ({})", todo);
            if (todo != null)
                return Response.status(Response.Status.OK).entity(todo).build();
            else return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Create a new task for the given todo. Name and description of task are taken from json string. Identity of the task is auto generated, Created time is taken from system current time. Default status is NOT-DONE.
     * @param idTodo                  identity of the todo
     * @param nameDescriptionTaskJson name and description of the task in json format
     *                                {
     *                                "name": "Name of the task",
     *                                "description": "description of the task"
     *                                }
     * @return HTTP Response for the create operation
     * Payload will look like this
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "Task name",
     * "description": "Description of the task",
     * "status": "NOT_DONE",
     * "created": "2016-12-07 05:42:29.809"
     * }
     */
    public Response createToDoTask(String idTodo, String nameDescriptionTaskJson) {
        try {
            log.debug("createToDoTask({})", idTodo);
            Map<String, Object> map = gson.fromJson(nameDescriptionTaskJson, new TypeToken<Map<String, String>>() {
            }.getType());
            Task task = ImmutableTask.builder().id(UUID.randomUUID().toString()).name(map.get(EntityType.name.name()).toString()).description(map.get(EntityType.description.name()).toString()).status(TaskType.NOT_DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
            log.debug("create a todo task from gson({})", task);
            Task task2 = (Task) todoApp.createTask(idTodo, task);
            log.debug("createToDoTask({})", task2);
            if (task2 != null)
                return Response.status(Response.Status.OK).entity(task2).build();
            else return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }


    /**
     * Update task with 3 properties from json string.
     *
     * @param idTodo                        id of a todo
     * @param idTask                        id of a task belong to the todo
     * @param nameDescriptionStatusTaskJson as json format
     *                                      {
     *                                      "name": "Name of the task",
     *                                      "description": "description of the task",
     *                                      "status": "DONE"
     *                                      }
     * @return HTTP Response code and a json formatted of the updated task
     * Payload will look like this
     * <p>
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "Task name",
     * "description": "Description of the task",
     * "status": "DONE",
     * "created": "2016-12-07 05:42:29.809"
     * }
     */
    public Response updateToDoTask(String idTodo, String idTask, String nameDescriptionStatusTaskJson) {
        try {
            log.debug("updateToDoTask({})", idTodo);
            Map<String, Object> map = gson.fromJson(nameDescriptionStatusTaskJson, new TypeToken<Map<String, String>>() {
            }.getType());
            Task task = ImmutableTask.builder().id(idTask).name(map.get(EntityType.name.name()).toString()).description(map.get(EntityType.description.name()).toString()).status(TaskType.valueOf(map.get(EntityType.status.name()).toString())).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
            log.debug("update a todo task from gson({})", task);
            Task task2 = (Task) todoApp.updateTask(idTodo, task);
            log.debug("updateToDoTask({})", task2);
            if (task2 != null)
                return Response.status(Response.Status.OK).entity(task2).build();
            else return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Remove a todo and related tasks
     *
     * @param idTodo todo identity
     * @return HTTP Repsonse code for the delete operation and empty body
     */
    public Response deleteTodoAndRelatedTasks(String idTodo) {
        if (todoApp.deleteTodoAndRelatedTasks(idTodo))
            return Response.status(Response.Status.OK).build();
        else return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    /**
     * Remove a todo task
     *
     * @param idTodo todo identity
     * @param idTask task identity
     * @return HTTP Repsonse code for the delete operation and empty body
     */
    public Response deleteTask(String idTodo, String idTask) {
        if (todoApp.deleteTask(idTodo, idTask))
            return Response.status(Response.Status.OK).build();
        else return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }


    /**
     * Get a list of known todos as a json formatted list
     * @return HTTP Response and a json formatted list of todos
     * Payload will look like this
     * <p>
     * [
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce01",
     * "name": "Todo name",
     * "created": "2016-12-07 05:42:29.809"
     * }
     * ]
     */
    public Response getTodos() {
        log.debug("getTodos()");
        return Response.status(Response.Status.OK).entity(todoApp.getTodos()).build();
    }

    /**
     * Get todo tasks
     *
     * @param idTodo id of a todo to get list of the todo tasks.
     * @return HTTP Response and a json formatted list of tasks belong to a todo
     * Payload will look like this
     * <p>
     * [
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "task name",
     * "description": "Description of the task",
     * "status": "NOT_DONE",
     * "created": "2016-12-07 05:42:29.809"
     * }
     * ]
     */
    public Response getTodoTasks(String idTodo, TaskType tasktype) {
        log.debug("getTodoTasks()");
        return Response.status(Response.Status.OK).entity(todoApp.getTodoTasks(idTodo, tasktype)).build();
    }

    /**
     * Get a todo task
     *
     * @param idTodo todo identity
     * @param idTask task identity
     * @return HTTP Repsonse code for the operation
     * Payload will look like this
     * <p>
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "task name",
     * "description": "Description of the task",
     * "status": "NOT_DONE",
     * "created": "2016-12-07 05:42:29.809"
     * }
     */
    public Response getTodoTask(String idTodo, String idTask) {
        log.debug("getTodoTask()");
        return Response.status(Response.Status.OK).entity(todoApp.getTodoTask(idTodo, idTask)).build();
    }
}
