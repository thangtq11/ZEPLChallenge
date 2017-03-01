package com.zepl.challenge.rest;

import com.zepl.challenge.Util;
import com.zepl.challenge.data.TaskType;
import javaslang.collection.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A REST implementation of the todo service.
 * Topology
 A Todo is a container of a list of Tasks. You can imagine a structure like this:
 - TODO
 - Task1
 - Task2
 - task3
 - etc
 */

@Path(Paths.TODOS)
@Controller
public class ToDoRestController {
    private final static Logger log = LoggerFactory.getLogger(ToDoRestController.class);

    @Inject
    private ToDoRestControllerImplement implement;

    /**
     * Create a new todo by name in json format. Identity of the todo is auto generated, Created time is taken from system current time.
     * POST /todos Create a new todo
     *
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
    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createToDo(String toDoNameJson) {
        log.info("createToDo(name:{})", toDoNameJson);
        if (toDoNameJson == null || toDoNameJson.isEmpty()|| !Util.isValidJSon(toDoNameJson))
            return Response.status(Response.Status.BAD_REQUEST).entity("json format is incorrect or null or empty when creating a todo.").build();
        return implement.createToDo(toDoNameJson);
    }

    /**
     * Create a new task for the given todo. Name and description of task are taken from json string. Identity of the task is auto generated, Created time is taken from system current time. Default status is NOT-DONE.
     * POST /todos/:todo_id/tasks
     *
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
    @POST
    @Path("/{" + Paths.ID_TODO + "}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createToDoTask(@PathParam(Paths.ID_TODO) String idTodo, String nameDescriptionTaskJson) {
        log.info("createToDoTask(idTodo:{} nameDescriptionTaskJson:{})", idTodo, nameDescriptionTaskJson);
        if (idTodo == null || idTodo.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("an invalid todo identity while creating a todo task ").build();
        Stream<String> stream = Stream.of(idTodo, nameDescriptionTaskJson).filter(s -> !s.isEmpty()).filter(p -> p != null);
        if (stream.size() != 2|| !Util.isValidJSon(nameDescriptionTaskJson))
            return Response.status(Response.Status.BAD_REQUEST).entity("json format is incorrect or null or empty while creating a todo task").build();
        return implement.createToDoTask(idTodo, nameDescriptionTaskJson);
    }


    /**
     * Get a list of known todos as a json formatted list
     * GET /todos Get a list of Todos
     *
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
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodos() {
        log.info("getTodos()");
        return implement.getTodos();
    }

    /**
     * GET /todos/:todo_id/tasks Get todo tasks
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
    @GET
    @Path("/{" + Paths.ID_TODO + "}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoTasks(@PathParam(Paths.ID_TODO) String idTodo) {
        log.info("getTodoTasks() with id:" + idTodo);
        if (idTodo == null || idTodo.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("an invalid todo identity while getting todo tasks ").build();
        return implement.getTodoTasks(idTodo, null);
    }

    /**
     * GET /todos/:todo_id/tasks/done Get a list of task that are done
     *
     * @param idTodo id of a todo to get list of the tasks.
     * @return HTTP Response and a json formatted list of done tasks belong to a todo
     * Payload will look like this
     * <p>
     * [
     * {
     * "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
     * "name": "task name",
     * "description": "Description of the task",
     * "status": "DONE",
     * "created": "2016-12-07 05:42:29.809"
     * }
     * ]
     */
    @GET
    @Path("/{" + Paths.ID_TODO + "}/tasks/done")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoTasksDone(@PathParam(Paths.ID_TODO) String idTodo) {
        log.info("getTodoTasksDone() with id:" + idTodo);
        if (idTodo == null || idTodo.isEmpty())
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity("an invalid todo identity for getting todo done tasks").build();
        return implement.getTodoTasks(idTodo, TaskType.DONE);
    }

    /**
     * GET /todos/:todo_id/tasks/not-done Get a list of task that are done
     *
     * @param idTodo id of a todo to get list of the tasks.
     * @return HTTP Response code and a json formatted list of not-done tasks belong to a todo
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
    @GET
    @Path("/{" + Paths.ID_TODO + "}/tasks/not-done")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoTasksNotDone(@PathParam(Paths.ID_TODO) String idTodo) {
        log.info("getTodoTasksDone() with id:" + idTodo);
        if (idTodo == null || idTodo.isEmpty())
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("an invalid todo identity for getting todo not done tasks").build();
        return implement.getTodoTasks(idTodo, TaskType.NOT_DONE);
    }

    /**
     * PUT /todos/:todo_id/tasks/:task_id Update task with 3 properties from json string.
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
    @PUT
    @Path("/{" + Paths.ID_TODO + "}/tasks" + "/{" + Paths.ID_TASK + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodoTask(@PathParam(Paths.ID_TODO) String idTodo, @PathParam(Paths.ID_TASK) String idTask, String nameDescriptionStatusTaskJson) {
        log.info("updateTodoTask(idTodo:{} idTask: {} nameDescriptionStatusTaskJson:{})", idTodo, idTask, nameDescriptionStatusTaskJson);
        Stream<String> stream = Stream.of(idTodo, idTask, nameDescriptionStatusTaskJson).filter(s -> !s.isEmpty()).filter(p -> p != null);
        if (stream.size() != 3 || !Util.isValidJSon(nameDescriptionStatusTaskJson))
            return Response.status(Response.Status.BAD_REQUEST).entity("json format is incorrect or null or empty while creating a todo task").build();
        return implement.updateToDoTask(idTodo, idTask, nameDescriptionStatusTaskJson);
    }



    /**
     * DELETE /todos/:todo_id Remove a todo and related tasks
     *
     * @param idTodo todo identity
     * @return HTTP Repsonse code for the delete operation and empty body
     */
    @DELETE
    @Path("/{" + Paths.ID_TODO + "}")
    public Response deleteTodo(@PathParam(Paths.ID_TODO) String idTodo) {
        log.info("deleteTodo(idTodo:{})", idTodo);
        if (idTodo == null || idTodo.isEmpty())
            return Response.status(Response.Status.FORBIDDEN).entity("an invalid todo identity when deleting a todo").build();
        return implement.deleteTodoAndRelatedTasks(idTodo);
    }

    /**
     * DELETE /todos/:todo_id/tasks/:task_id Remove a todo task
     *
     * @param idTodo todo identity
     * @param idTask task identity
     * @return HTTP Repsonse code for the delete operation and empty body
     */
    @DELETE
    @Path("/{" + Paths.ID_TODO + "}/tasks/{" + Paths.ID_TASK + "}")
    public Response deleteTask(@PathParam(Paths.ID_TODO) String idTodo, @PathParam(Paths.ID_TASK) String idTask) {
        log.info("deleteTask(idTodo:{}, idTask: {} )", idTodo, idTask);
        Stream<String> stream = Stream.of(idTodo, idTask).filter(s -> !s.isEmpty()).filter(p -> p != null);
        if (stream.size() != 2)
            return Response.status(Response.Status.FORBIDDEN).entity("an invalid todo identity or task identity when deleting a todo task").build();
        return implement.deleteTask(idTodo, idTask);
    }

    /**
     * GET /todos/:todo_id/tasks/:task_id Get a todo task
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
    @GET
    @Path("/{" + Paths.ID_TODO + "}/tasks/{" + Paths.ID_TASK + "}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam(Paths.ID_TODO) String idTodo, @PathParam(Paths.ID_TASK) String idTask) {
        log.info("getTask(idTodo:{}, idTask: {} )", idTodo, idTask);
        Stream<String> stream = Stream.of(idTodo, idTask).filter(s -> !s.isEmpty()).filter(p -> p != null);
        if (stream.size() != 2)
            return Response.status(Response.Status.BAD_REQUEST).entity("an invalid todo identity or task identity when getting a todo task").build();
        return implement.getTodoTask(idTodo, idTask);
    }

}
