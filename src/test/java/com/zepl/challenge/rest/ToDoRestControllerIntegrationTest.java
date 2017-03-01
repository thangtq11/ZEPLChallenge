package com.zepl.challenge.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zepl.challenge.data.EntityType;
import com.zepl.challenge.data.Task;
import com.zepl.challenge.data.TaskType;
import com.zepl.challenge.data.ToDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ToDoRestControllerIntegrationTest {

    private final static Logger log = LoggerFactory.getLogger(ToDoRestControllerIntegrationTest.class);
    @Inject
    private ToDoRestControllerImplement todoResource;
    @Inject
    private ToDoRestController todoCollector;
    @Inject
    private Gson gson;

    @Test
    public void getTodosShouldReturnListOfTodos() {
        Response response = todoCollector.getTodos();
        log.debug("response for getTodosShouldReturnListOfTodos: {} entity: {}", response, response.getEntity());
        List<ToDo> todoList = (List) response.getEntity();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isNotNull().isInstanceOf(java.util.List.class);
    }

    @Test
    public void getIncorrectTodoIDShouldReturnError() {
        Response response = todoCollector.getTodoTasks("12312331");
        log.debug("response for getIncorrectTodoIDShouldReturnError: {} entity: {}", response, response.getEntity());
        List<ToDo> todoList = (List) response.getEntity();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isNotNull().isInstanceOf(java.util.List.class);
    }

    public List getTodoTasksShouldReturnListOfTodoTasks(String idTodo) {
        Response response = todoCollector.getTodoTasks(idTodo);
        log.debug("response for getTodoTasksShouldReturnListOfTodoTasks: {} entity: {} ", response, response.getEntity());
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isNotNull().isInstanceOf(java.util.List.class);
        List<Object> list = (List) (response.getEntity());
        return list;
    }

    public List getTodoDoneTasksShouldReturnListOfTodoDoneTasks(String idTodo) {
        Response response = todoCollector.getTodoTasksDone(idTodo);
        log.debug("response for getTodoTasksShouldReturnListOfTodoTasks: {} entity: {}", response, response.getEntity());
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isNotNull().isInstanceOf(java.util.List.class);
        List<Object> list = (List) (response.getEntity());
        return list;
    }

    public List getTodoNotDoneTasksShouldReturnListOfTodoNotDoneTasks(String idTodo) {
        Response response = todoCollector.getTodoTasksNotDone(idTodo);
        log.debug("response for getTodoTasksShouldReturnListOfTodoTasks: {} entity: {}", response, response.getEntity());
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isNotNull().isInstanceOf(java.util.List.class);
        List<Object> list = (List) (response.getEntity());
        return list;
    }

    @Test
    public void createATodoByNameShouldReturnATodoTest() {
        String nameTodoJson = "{\n" +
                "         \"name\": \"Name of the todo\"\n" +
                "        }\n";
        ToDo todo = (ToDo) createATodoByNameShouldReturnATodo(nameTodoJson);
        deleteTodoAndRelatedTasks(todo.id());

    }

    public ToDo createATodoByNameShouldReturnATodo(String jsonName) {
        Map<String, Object> map = gson.fromJson(jsonName, new TypeToken<Map<String, String>>() {
        }.getType());
        Response response = todoCollector.createToDo(jsonName);
        assertThat(response.getStatus()).isEqualTo(200);
        ToDo todo = (ToDo) response.getEntity();
        log.debug("createATodoByNameShouldReturnATodo {}", todo);
        assertThat(todo.name()).isNotNull().isEqualTo(map.get(EntityType.name.name()).toString());
        return todo;
    }

    public Task getATodoTaskByIDShouldReturnATodoTask(String idTodo, String idTask) {
        log.debug("getATodoTaskByIDShouldReturnATodoTask idTodo:" + idTodo + " idTask:" + idTask);
        Response response = todoCollector.getTask(idTodo, idTask);
        assertThat(response.getStatus()).isEqualTo(200);
        Task task = (Task) response.getEntity();
        log.debug("getATodoTaskByIDShouldReturnATodoTask {}", task);
        assertThat(task.id()).isNotNull().isEqualTo(idTask);
        return task;
    }

    public Task createATodoTaskByNameShouldReturnATodoTask(String idTodo, String gsonNameDescription) {

        Map<String, Object> map = gson.fromJson(gsonNameDescription, new TypeToken<Map<String, String>>() {
        }.getType());
        Response response = todoCollector.createToDoTask(idTodo, gsonNameDescription);
        assertThat(response.getStatus()).isEqualTo(200);
        Task task = (Task) response.getEntity();
        log.debug("createATodoTaskByNameShouldReturnATodoTask {}", task);
        assertThat(task.name()).isNotNull().isEqualTo(map.get(EntityType.name.name().toString()));
        assertThat(task.description()).isNotNull().isEqualTo(map.get(EntityType.description.name().toString()));
        return task;
    }

    public Task updateATodoTaskByNameShouldReturnATodoTask(String idTodo, String idTask, String nameDescriptionStatusTask) {
        Map<String, String> map = gson.fromJson(nameDescriptionStatusTask, new TypeToken<Map<String, String>>() {
        }.getType());
        Response response = todoCollector.updateTodoTask(idTodo, idTask, nameDescriptionStatusTask);
        assertThat(response.getStatus()).isEqualTo(200);
        Task task = (Task) response.getEntity();
        log.debug("result of updateATodoTaskByNameShouldReturnATodoTask {}", task);
        assertThat(task.name()).isNotNull().isEqualTo(map.get(EntityType.name.name().toString()));
        assertThat(task.description()).isNotNull().isEqualTo(map.get(EntityType.description.name().toString()));
        assertThat(task.status()).isNotNull().isEqualTo(
                TaskType.valueOf(map.get(EntityType.status.name().toString())));
        return task;
    }

    public void deleteTask(String idTodo, String idTask) {
        Response response = todoCollector.deleteTask(idTodo, idTask);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    public void deleteTodoAndRelatedTasks(String idTodo) {
        Response response = todoCollector.deleteTodo(idTodo);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void testACRUDToDoTask() throws InterruptedException {
        String idTodo = UUID.randomUUID().toString();
        Thread.sleep(1000);
        String idTask = UUID.randomUUID().toString();

        //create a todo
        String nameTodoJson = "{\n" +
                "         \"name\": \"Name of the todo\"\n" +
                "        }\n";
        ToDo todo = createATodoByNameShouldReturnATodo(nameTodoJson);
        //create a todo task
        String gsonNameDescriptionTask = " {\n" +
                "         \"name\": \"Name of the task\",\n" +
                "         \"description\": \"description of the task\"\n" +
                "        }";
        List<Task> listTasks = new ArrayList<>();
        Task task1 = createATodoTaskByNameShouldReturnATodoTask(todo.id(), gsonNameDescriptionTask);
        listTasks.add(task1);
        Task task2 = createATodoTaskByNameShouldReturnATodoTask(todo.id(), gsonNameDescriptionTask);
        listTasks.add(task2);
        Task task3 = createATodoTaskByNameShouldReturnATodoTask(todo.id(), gsonNameDescriptionTask);
        listTasks.add(task3);
        Task task4 = createATodoTaskByNameShouldReturnATodoTask(todo.id(), gsonNameDescriptionTask);
        listTasks.add(task4);
        listTasks.forEach(T -> getATodoTaskByIDShouldReturnATodoTask(todo.id(), T.id()));
        //update done tasks
        String nameDescriptionStatusTask = " {\n" +
                "           \"name\": \"Name of the task\",\n" +
                "           \"description\": \"description of the task\",\n" +
                "           \"status\": \"DONE\"\n" +
                "        }";
        Task task5 = updateATodoTaskByNameShouldReturnATodoTask(todo.id(), task1.id(), nameDescriptionStatusTask);
        Task task6 = updateATodoTaskByNameShouldReturnATodoTask(todo.id(), task2.id(), nameDescriptionStatusTask);
        //get all todos;
        getTodosShouldReturnListOfTodos();
        //get all tasks;
        List<Task> list = getTodoTasksShouldReturnListOfTodoTasks(todo.id());
        list.forEach((o) -> log.debug(" printing task: {}", o));
        assertThat(list.size()).isEqualTo(4);
        //get all done tasks;
        list = getTodoDoneTasksShouldReturnListOfTodoDoneTasks(todo.id());
        list.forEach((o) -> log.debug(" printing done tasks: {}", o));
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.stream().anyMatch(t -> t.id().equals(task1.id()))).isTrue();
        assertThat(list.stream().anyMatch(t -> t.id().equals(task2.id()))).isTrue();

        //get all not-done tasks;
        list = getTodoNotDoneTasksShouldReturnListOfTodoNotDoneTasks(todo.id());
        list.forEach((o) -> log.debug(" printing not-done tasks: {}", o));
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.stream().anyMatch(t -> t.id().equals(task3.id()))).isTrue();
        assertThat(list.stream().anyMatch(t -> t.id().equals(task4.id()))).isTrue();
        //delete done task
        deleteTask(todo.id(), task1.id());
        deleteTask(todo.id(), task2.id());
        list = getTodoDoneTasksShouldReturnListOfTodoDoneTasks(todo.id());
        assertThat(list.size()).isEqualTo(0);
        //delete a todo and related tasks
        deleteTodoAndRelatedTasks(todo.id());
        list = getTodoTasksShouldReturnListOfTodoTasks(todo.id());
        assertThat(list.size()).isEqualTo(0);


    }


    public  void createATodoByInvalidJsonShouldReturnError400(String invalidJson)
    {
        Response response = todoCollector.createToDo(invalidJson);
        log.debug("createATodoByInvalidJsonNameShouldReturnError400: status:", response.getStatus()+ " entity:"+response.getEntity()) ;
        assertThat(response.getStatus()).isEqualTo(400);
    }
    public  void createATodoTaskByInvalidJsonShouldReturnError400(String idTodo,String invalidJson)
    {
        Response response = todoCollector.createToDoTask(idTodo,invalidJson);
        log.debug("createATodoTaskByInvalidJsonShouldReturnError400: status:", response.getStatus()+ " entity:"+response.getEntity()) ;
        assertThat(response.getStatus()).isEqualTo(400);
    }

    public  void updateATodoTaskByInvalidJsonShouldReturnError400(String idTodo,String idTask,String invalidJson)
    {
        Response response = todoCollector.updateTodoTask(idTodo,idTask,invalidJson);
        log.debug("updateATodoTaskByInvalidJsonShouldReturnError400: status:", response.getStatus()+ " entity:"+response.getEntity()) ;
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void testFailureCases() {
        try {
            log.info("testFailureCases................");
            //for testing data
            String nameTodoJson = "{\n" +
                    "         \"name\": \"Name of the todo\"\n" +
                    "        }\n";
            String gsonNameDescriptionTask = " {\n" +
                    "         \"name\": \"Name of the task\",\n" +
                    "         \"description\": \"description of the task\"\n" +
                    "        }";
            ToDo todo = createATodoByNameShouldReturnATodo(nameTodoJson);
            Task task = createATodoTaskByNameShouldReturnATodoTask(todo.id(),gsonNameDescriptionTask);
            //create todo and task with invalid json format
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
            createATodoByInvalidJsonShouldReturnError400(invalidNameTodoJson);
            createATodoTaskByInvalidJsonShouldReturnError400(todo.id(),invalidGsonNameDescriptionTask);
            updateATodoTaskByInvalidJsonShouldReturnError400(todo.id(),task.id(),invalidGsonNameDescriptionStatusTask);
            deleteTodoAndRelatedTasks(todo.id());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }




}