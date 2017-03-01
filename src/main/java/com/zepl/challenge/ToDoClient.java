package com.zepl.challenge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.zepl.challenge.data.ImmutableTask;
import com.zepl.challenge.data.ImmutableToDo;
import com.zepl.challenge.data.Task;
import com.zepl.challenge.data.ToDo;
import com.zepl.challenge.rest.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javaslang.collection.Stream;


/**
 * A reference implementation for the todo client.
 * Consumers of the REST API can look at ToDoClient to understand API semantics.
 * This existing client populates the todo service with json datatype
 * useful for testing.
 *
 */
public class ToDoClient implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(ToDoClient.class);
    /**
     *
     */
    private String hostTodoService;
    private String portTodoService;
    private static String BASE_URI;
    private final Gson gson;
    public static Map<Response, Object> statisticsMap = new ConcurrentHashMap();
    /**
     * end point to supply updates
     */
    private WebTarget collect;
    /**
     * statistics success and error reponses from the server
     */
    private static AtomicInteger numRequests;
    public static AtomicInteger totalSessions = new AtomicInteger();
    public static AtomicInteger counterSessions = new AtomicInteger();
    public static LocalDateTime startTime;
    public static boolean enableTestFailure=false;


    public ToDoClient(String host,String port) {
        hostTodoService=host;
        portTodoService=port;
        BASE_URI="http://"+hostTodoService+":"+portTodoService;
        Client client = ClientBuilder.newClient();
        gson = gsonBuilder();
        collect = client.target(BASE_URI + "");
        numRequests = new AtomicInteger(0);
    }
    private static Gson gsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }
        return gsonBuilder.create();
    }


    /**
     * for implementing a black box test function.
     * leveling of test is auto grade with
     * one session -> multi sessions -> stress test
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args == null ) {
                usage();
                System.exit(1);
            }
            AtomicInteger numThreads = new AtomicInteger(1);
            AtomicInteger numSessions = new AtomicInteger(0);
            Stream<String> stream= Stream.of(args).filter(p->!p.isEmpty()).filter(p->p!=null);
            log.info("<<<<<<<<<<<<<<<<-Todo Client->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info(" This is a todo client for testing a restful todo service");
            stream.forEach(s->log.info("printing argument:"+s));
            try {
                InetAddress iAddress = InetAddress.getByName(args[0]);
                numThreads.set(Integer.parseInt(args[2]));
                numSessions.set(Integer.parseInt(args[3]));
            } catch (Exception e) {
                usage();
                System.exit(1);
            }
            if(numSessions.get()<1|| stream.size() != 4 || ValueRange.of(0,65556).isValidValue(Long.parseLong(args[1]))==false)
            {
                usage();
                System.exit(1);
            }

            ToDoClient wc = new ToDoClient(args[0],args[1]);
            wc.startTime = LocalDateTime.now();
            totalSessions.set(numSessions.get());
            counterSessions.set(numSessions.get());
            //
            ExecutorService executor = Executors.newFixedThreadPool(numThreads.get());
            IntStream.range(0,numThreads.get()).forEach(i->
            {
                Runnable worker = new ToDoClient(args[0],args[1]);
                executor.execute(worker);
            });
            // This will make the executor accept no new threads
            // and finish all existing threads in the queue
            executor.shutdown();
            // Wait until all threads are finish
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            //
            wc.statistics();
            log.info("complete");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
    public static void usage() {
        log.error(" This is a todo client for testing a restful todo service");
        log.error(" An argument are not a valid input");
        log.error(" Argument number of sessions must be an integer and > 0");
        log.error(" please usage:  TodoClient [ip address of the service] [port] [concurrent threads] [number of sessions: 1 (1 session = [11 requests + 11 responses]) ] ");
        log.error(" exp:  TodoClient 10.30.164.132 8084 2 3");
        System.exit(1);
    }
    @Override
    public void run()
    {
        try{
            while(counterSessions.decrementAndGet()>=0) {
                testCRUDTodoTask();
                testFailureCases();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }


    /**
     * testing Add a new todo to the known todo list.
     *
     * @param todoName name of the todo
     * @return HTTP Response code for the add operation
     */
    public Map createToDo(String todoName) {
        WebTarget path = collect.path(Paths.TODOS);
        ToDo todo = gson.fromJson(todoName, ToDo.class);
        numRequests.incrementAndGet();
        Response response = path.request().post(Entity.entity(todo, "application/json"));
        log.info("collect.createToDo: " + todoName + " metadata:" + response.getMetadata() + " status:" + response.getStatus() + " from URI:" + collect.getUri() + collect.toString());
        ToDo todo2 = (ToDo) response.readEntity(ToDo.class);
        Map<Response, ToDo> map = new HashMap<>();
        map.put(response, todo2);
        statisticsMap.put(response, todo2);
        return map;
    }

    public void createToDoWithInvalidJson(String todoName) {
        WebTarget path = collect.path(Paths.TODOS);
        numRequests.incrementAndGet();
        Response response = path.request().post(Entity.entity(todoName, "application/text"));
        log.info("collect.createToDoWithInvalidJson: " + todoName + " metadata:" + response.getMetadata() + " status:" + response.getStatus() + " getEntity:" +  response.getEntity());
        statisticsMap.put(response, response.getEntity());
    }

    public void createTaskWithInvalidJson(String idTodo,String gsonNameDescriptionTask) {
        WebTarget path = collect.path(Paths.TODOS+"/" + idTodo + "/tasks");
        numRequests.incrementAndGet();
        Response response = path.request().post(Entity.entity(gsonNameDescriptionTask, "application/text"));
        log.info("collect.createTaskWithInvalidJson: " + gsonNameDescriptionTask + " metadata:" + response.getMetadata() + " status:" + response.getStatus() + " getEntity:" +  response.getEntity());
        statisticsMap.put(response, response.getEntity());
    }

    public void updateTaskWithInvalidJson(String idTodo,String idTask,String nameDescriptionStatusTask) {
        WebTarget path = collect.path(Paths.TODOS+"/" + idTodo + "/tasks/"+idTask);
        numRequests.incrementAndGet();
        Response response = path.request().put(Entity.entity(nameDescriptionStatusTask, "application/text"));
        log.info("collect.updateTaskWithInvalidJson: " + nameDescriptionStatusTask + " metadata:" + response.getMetadata() + " status:" + response.getStatus() + " getEntity:" +  response.getEntity());
        statisticsMap.put(response, response.getEntity());
    }

    /**
     * Add a new task
     * POST /todos/:todo_id/tasks Create a new task for the given todo
     *
     * @param idTodo
     * @param nameDescriptionTaskJson {
     *                                "name": "Name of the task",
     *                                "description": "description of the task"
     *                                }
     * @return
     */
    public Map createToDoTask(String idTodo, String nameDescriptionTaskJson) {
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks");
        Task task = gson.fromJson(nameDescriptionTaskJson, Task.class);
        Response response = path.request().post(Entity.entity(task, "application/json"));
        log.info("collect.createToDoTask: " + idTodo + " nameDescriptionTaskJson:" + nameDescriptionTaskJson + " status:" + response.getStatus());
        Task resultTask = (Task) response.readEntity(Task.class);
        Map<Response, Task> map = new HashMap<>();
        map.put(response, resultTask);
        statisticsMap.put(response, resultTask);
        return map;
    }


    /**
     * Return a list of known todos as a json formatted list
     *
     * @return HTTP Response code and a json formatted list of todos
     */
    public Response getTodos() {
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS);
        Response response = path.request().get();
        List<ToDo> todoList = (List) response.readEntity(List.class);
        log.info("getTodos: status:" + response.getStatus() + "todoList:" + todoList);
        statisticsMap.put(response, todoList);
        return response;
    }

    /**
     * GET /todos/:todo_id/tasks Get todo tasks
     *
     * @param idTodo id of a todo to get list of the tasks.
     * @return HTTP Response code and a json formatted list of tasks belong to a todo
     */
    public Response getTodoTasks(String idTodo) {
        log.info("getTodoTasks: idTodo:" + idTodo);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks");
        Response response = path.request().get();
        List<Task> taskList = (List) response.readEntity(List.class);
        log.info("getTodoTasks: status:" + response.getStatus() + "taskList:" + taskList);
        statisticsMap.put(response, taskList);
        return response;
    }

    /**
     * GET /todos/:todo_id/tasks/done Get a list of task that are done
     *
     * @param idTodo id of a todo to get list of the tasks.
     * @return HTTP Response code and a json formatted list of done tasks belong to a todo
     */
    public Response getTodoDoneTasks(String idTodo) {
        log.info("getTodoDoneTasks: idTodo:" + idTodo);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks/done");
        Response response = path.request().get();
        List<Task> taskList = (List) response.readEntity(List.class);
        log.info("getTodoDoneTasks: status:" + response.getStatus() + "taskList:" + taskList);
        statisticsMap.put(response, taskList);
        return response;
    }

    /**
     * GET /todos/:todo_id/tasks/not-done Get a list of task that are done
     *
     * @param idTodo id of a todo to get list of the tasks.
     * @return HTTP Response code and a json formatted list of done tasks belong to a todo
     */
    public Response getTodoNotDoneTasks(String idTodo) {
        log.info("getTodoNotDoneTasks: idTodo:" + idTodo);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks/not-done");
        Response response = path.request().get();
        List<Task> taskList = (List) response.readEntity(List.class);
        log.info("getTodoNotDoneTasks: status:" + response.getStatus() + "taskList:" + taskList);
        statisticsMap.put(response, taskList);
        return response;
    }

    /**
     * GET /todos/:todo_id/tasks/:task_id Get a todo task
     *
     * @param idTodo todo identity
     * @param idTask task identity
     * @return HTTP Response code and a json formatted list of a task belong to a todo
     */
    public Response getTodoTask(String idTodo, String idTask) {
        log.info("getTodoTask: idTodo:" + idTodo + " idTask:" + idTask);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks/" + idTask);
        Response response = path.request().get();
        Task resultTask = (Task) response.readEntity(Task.class);
        log.info("getTodoTask: status:" + response.getStatus() + "resultTask:" + resultTask);
        statisticsMap.put(response, resultTask);
        return response;
    }

    /**
     * PUT /todos/:todo_id/tasks/:task_id Update task
     *
     * @param idTodo                        id of a todo
     * @param idTask                        id of a task belong to the todo
     * @param nameDescriptionStatusTaskJson as below
     *                                      {
     *                                      "name": "Name of the task",
     *                                      "description": "description of the task",
     *                                      "status": "DONE"
     *                                      }
     * @return HTTP Response code and a json formatted list of not-done tasks belong to a todo
     */
    public Response updateTodoTask(String idTodo, String idTask, String nameDescriptionStatusTaskJson) {
        log.info("updateTodoTask: idTodo:" + idTodo + " idTask:" + idTask);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks/" + idTask);
        Task task = gson.fromJson(nameDescriptionStatusTaskJson, Task.class);
        Response response = path.request().put(Entity.entity(task, "application/json"));
        Task resultTask = (Task) response.readEntity(Task.class);
        log.info("updateTodoTask: status:" + response.getStatus() + "resultTask:" + resultTask);
        statisticsMap.put(response, resultTask);
        return response;
    }


    /**
     * testing Add a new todo to the known todo list.
     *
     * @param name name the todo
     * @return HTTP Response code for the add operation
     */
    public ToDo createAToDo(String name) {
        ToDo todo = ImmutableToDo.builder().id(UUID.randomUUID().toString()).name(name)
                .created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
        return todo;
    }


    /**
     * DELETE /todos/:todo_id Remove a toto and releated task
     *
     * @param idTodo todo identity
     * @return HTTP Repsonse code for the delete operation and empty body
     */
    public Response deleteTodo(String idTodo) {
        log.info("deleteTodo: idTodo:" + idTodo);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo);
        Response response = path.request().delete();
        log.info("deleteTodo: status:" + response.getStatus());
        statisticsMap.put(response, ImmutableToDo.builder().id(idTodo).name("deleted todo name").build());
        return response;
    }

    /**
     * DELETE /todos/:todo_id/tasks/:task_id Remove task
     *
     * @param idTodo todo identity
     * @param idTask task identity
     * @return HTTP Repsonse code for the delete operation
     */
    public Response deleteTodoTask(String idTodo, String idTask) {
        log.info("deleteTodoTask: idTodo:" + idTodo + " idTask:" + idTask);
        numRequests.incrementAndGet();
        WebTarget path = collect.path(Paths.TODOS + "/" + idTodo + "/tasks/" + idTask);
        Response response = path.request().delete();
        log.info("deleteTodoTask: status:" + response.getStatus());
        statisticsMap.put(response, ImmutableTask.builder().id(idTask).name("deleted todotask name").build());
        return response;
    }
    /**
     * test a full test case , it's combined of create, update query and delete transactions
     */
    public boolean testCRUDTodoTask() {
        boolean result=false;
        try {
            log.info("testCRUDTodoTask................");
            //create todo
            String nameTodoJson = "{\n" +
                    "         \"name\": \"Name of the todo\"\n" +
                    "        }\n";
            String gsonNameDescriptionTask = " {\n" +
                    "         \"name\": \"Name of the task\",\n" +
                    "         \"description\": \"description of the task\"\n" +
                    "        }";
            //update done tasks
            String nameDescriptionStatusTask = " {\n" +
                    "           \"name\": \"Name of the task\",\n" +
                    "           \"description\": \"description of the task\",\n" +
                    "           \"status\": \"DONE\"\n" +
                    "        }";
            ToDo todo = createAToDo(nameTodoJson);
            Map<Response, ToDo> map = createToDo(todo.name());
            todo = map.entrySet().iterator().next().getValue();
            getTodos();
            //creat tasks for the todo
            Map<Response, Task> mapTask = createToDoTask(todo.id(), gsonNameDescriptionTask);
            Map<Response, Task> mapTask2 = createToDoTask(todo.id(), gsonNameDescriptionTask);
            //get the todo tasks
            getTodoTasks(todo.id());
            //get a todo task
            Task task = mapTask.entrySet().iterator().next().getValue();
            getTodoTask(todo.id(), task.id());
            //update a todo task
            updateTodoTask(todo.id(), task.id(), nameDescriptionStatusTask);
            //get the done tasks
            getTodoDoneTasks(todo.id());
            //get the not-done tasks
            getTodoNotDoneTasks(todo.id());
            //delete a todo task
            deleteTodoTask(todo.id(), task.id());
            //delete todo and related tasks
            deleteTodo(todo.id());
            result=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return result;
    }


    /**
     * test a full failure test case , it's combined of create, update query and delete transactions
     */
    public boolean testFailureCases() {
        boolean result=false;
        try {
            enableTestFailure=true;
            log.info("testFailureCases................enableTestFailure:"+enableTestFailure);
            //for testing data
            String nameTodoJson = "{\n" +
                    "         \"name\": \"Name of the todo\"\n" +
                    "        }\n";
            String gsonNameDescriptionTask = " {\n" +
                    "         \"name\": \"Name of the task\",\n" +
                    "         \"description\": \"description of the task\"\n" +
                    "        }";
            Map<Response, ToDo> map = createToDo(nameTodoJson);
            ToDo todo = map.entrySet().iterator().next().getValue();
            Map<Response, Task> mapTask = createToDoTask(todo.id(), gsonNameDescriptionTask);
            //get a todo task
            Task task = mapTask.entrySet().iterator().next().getValue();
            //create todo
            String invalidNameTodoJson = "{\n" +
                    "         \"\": \"Name of the todo\"\n" +
                    "        }\n";
            String invalidGsonNameDescriptionTask = " {\n" +
                    "         \"name\": \"Name of the task\",\n" +
                    "         \"\": \"description of the task\"\n" +
                    "        }";
            //update done tasks
            String invalidGsonNameDescriptionStatusTask = " {\n" +
                    "           \"name\": \"Name of the task\",\n" +
                    "           \"description\": \"description of the task\",\n" +
                    "           \"status\": \"\"\n" +
                    "        }";
            createToDoWithInvalidJson(invalidNameTodoJson);
            createTaskWithInvalidJson(todo.id(),invalidGsonNameDescriptionTask);
            updateTaskWithInvalidJson(todo.id(),task.id(),invalidGsonNameDescriptionStatusTask);
            //free data
            deleteTodo(todo.id());
            result=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return result;
    }


    public void statistics() {
        try {

            Duration duration = Duration.between(startTime, LocalDateTime.now());
            Long seconds=((duration.getSeconds()==0)? 1:duration.getSeconds());
            //checking if error occur
            Map<Integer, Long> mapTemp = statisticsMap.entrySet().stream()
                    .collect(Collectors.groupingBy(o -> o.getKey().getStatus(), Collectors.counting()));
            Long totalResponses=mapTemp.values().stream().reduce(0L,Long::sum);
            log.info("<<<<<<<<<<<<<<<<-Begin Statistics->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //total of request
            log.info("started time:" + startTime);
            log.info("stopped time:" + LocalDateTime.now());
            log.info("duration:" + duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase());
            log.info("total of sessions:"+totalSessions.get()+" sessions per second :"+totalSessions.get()/seconds);
            log.info("total of sending requests:"+numRequests.get()+" requests per second :"+numRequests.get()/seconds);
            mapTemp.forEach((k, v) -> {
                log.info("Response Status:" + k + " number of responses:" + v + " responses per second :"+v/seconds);
            });
            if(totalResponses != numRequests.get())
            {
                log.error("the total requests are not matched with the total responses : "+totalResponses);
                throw new RuntimeException("the integration test is not pass");
            }
                mapTemp.forEach((k, v) -> {
                if (k != 200 && !enableTestFailure) {
                    log.error("some integration tests are not pass");
                    throw new RuntimeException("the integration test is not pass");
                }
            });
            log.info("<<<<<<<<<<<<<<<<-End Statistics->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
