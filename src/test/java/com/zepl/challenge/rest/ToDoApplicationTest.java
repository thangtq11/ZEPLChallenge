package com.zepl.challenge.rest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zepl.challenge.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author code test administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ToDoApplicationTest{

    private final static Logger log = LoggerFactory.getLogger(ToDoApplicationTest.class);
    private ToDoApplication todoApp;
    public ToDoApplicationTest()
    {
        Injector injector = Guice.createInjector(new TodoAppInjector());
        todoApp = injector.getInstance(ToDoApplication.class);
    }
    @Test
    public  void testGetTodos()
    {
        log.debug("testGetTodos()");
        String todoName="test name of a todo";
        Entity todo = todoApp.create(todoName);
        List list = todoApp.getTodos();
        assertThat(list.size()).isGreaterThan(0);
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }
    @Test
    public  void testGetTodoTasks()
    {
        log.debug("testGetTodoTasks()");
        Entity todo = todoApp.create("test name of a todo");
        Entity task = todoApp.createTask(todo.id(),ImmutableTask.builder().id(UUID.randomUUID().toString()).name("name of the task").description("description of the task").status(TaskType.NOT_DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build());
        List<Task> list = todoApp.getTodoTasks(todo.id(),null);
        list.forEach(t->assertThat(t.id()).isEqualTo(task.id()));
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }
    @Test
    public  void testGetTodoTasksDone()
    {
        log.debug("testGetTodoTasksDone()");
        Entity todo = todoApp.create("test name of a todo");
        Entity task = todoApp.createTask(todo.id(),ImmutableTask.builder().id(UUID.randomUUID().toString()).name("name of the task").description("description of the task").status(TaskType.DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build());
        List<Task> list = todoApp.getTodoTasks(todo.id(),null);
        list.forEach(t->assertThat(t.id()).isEqualTo(task.id()));
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }
    @Test
    public  void testGetTodoTasksNotDone()
    {
        log.debug("testGetTodoTasksDone()");
        Entity todo = todoApp.create("test name of a todo");
        Entity task = todoApp.createTask(todo.id(),ImmutableTask.builder().id(UUID.randomUUID().toString()).name("name of the task").description("description of the task").status(TaskType.NOT_DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build());
        List<Task> list = todoApp.getTodoTasks(todo.id(),null);
        list.forEach(t->assertThat(t.id()).isEqualTo(task.id()));
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }
    @Test
    public  void testCreateATodo()
    {
        log.debug("testCreateATodo()");
        String todoName="test name of a todo";
        Entity todo = todoApp.create(todoName);
        assertThat(todo.name()).isEqualTo(todoName);
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }
    @Test
    public  void testCreateATodoTask()
    {
        log.debug("testCreateATodoTask()");
        String idTask=UUID.randomUUID().toString();
        Entity todo = todoApp.create("test name of a todo");
        Task task = ImmutableTask.builder().id(idTask).name("name of the task").description("description of the task").status(TaskType.NOT_DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
        Entity returnTask = todoApp.createTask(todo.id(),task);
        assertThat(returnTask.id()).isEqualTo(idTask);
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }

    @Test
    public  void testUpdateATodoTask()
    {
        log.debug("testUpdateATodoTask()");
        String idTask=UUID.randomUUID().toString();
        Entity todo = todoApp.create("test name of a todo");
        Task task = ImmutableTask.builder().id(idTask).name("name of the task").description("description of the task").status(TaskType.NOT_DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
        Entity returnTask = todoApp.createTask(todo.id(),task);
        Task taskUpdate = ImmutableTask.builder().id(idTask).name("name of the task").description("description of the task new").status(TaskType.DONE).created((new java.sql.Timestamp(new java.util.Date().getTime())).toString()).build();
        Task returnTaskUpdate = (Task)todoApp.updateTask(todo.id(),taskUpdate);
        assertThat(returnTaskUpdate.id()).isEqualTo(idTask);
        assertThat(returnTaskUpdate.status()).isEqualTo(TaskType.DONE);
        todoApp.deleteTodoAndRelatedTasks(todo.id());
    }




}


