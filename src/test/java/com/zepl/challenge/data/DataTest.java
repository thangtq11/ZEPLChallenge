package com.zepl.challenge.data;

import com.google.gson.Gson;
import com.zepl.challenge.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by THANGTQ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataTest {
    @Inject
    private Gson gson;

    @Test
    public void testTodoFromNameGson()
    {
        String nameTodoJson = "{\n" +
                "         \"name\": \"Name of the todo\"\n" +
                "        }\n";
        ToDo todo = gson.fromJson(nameTodoJson,ToDo.class);
        Util util=new Util();
        assertThat(util.isValidJSon(nameTodoJson)).isEqualTo(true);
        assertThat(todo.name()).isNotEmpty();

        String invalidNameTodoJson = "{\n" +
                "         \"name\" \"Name of the todo\"\n" +
                "        }\n";
        assertThat(util.isValidJSon(invalidNameTodoJson)).isEqualTo(false);

        invalidNameTodoJson = "{\n" +
                "         \"\": \"Name of the todo\"\n" +
                "        }\n";
        assertThat(util.isValidJSon(invalidNameTodoJson)).isEqualTo(false);
    }
    @Test
    public void testTaskFromNameDescriptionStatusTaskGsonDone()
    {
        String gsonNameDescriptionStatusTask = "  {\n" +
                "            \"id\": \"84569ae0-bc41-11e6-a4a6-cec0c932ce03\",\n" +
                "            \"name\": \"task name\",\n" +
                "            \"description\": \"Description of the task\",\n" +
                "            \"status\": \"NOT_DONE\",\n" +
                "            \"created\": \"2016-12-07 05:42:29.809\"\n" +
                "        }";
        assertThat(Util.isValidJSon(gsonNameDescriptionStatusTask)).isEqualTo(true);
        Task task = gson.fromJson(gsonNameDescriptionStatusTask,Task.class);
        assertThat(task.name()).isNotEmpty();
    }

}
