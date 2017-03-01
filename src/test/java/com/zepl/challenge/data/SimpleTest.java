package com.zepl.challenge.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by THANGTQ
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleTest {
    Gson gson = new Gson();

    @Test
    public void testGson()
    {
        System.out.println("EntityType.id.name:"+EntityType.id.name());

        Timestamp tmp= new java.sql.Timestamp(new java.util.Date().getTime());
        System.out.println("Timestamp:"+tmp.toString());
        String json="{\n" +
                "         \"name\": \"Name of the todo\"\n" +
                "        }\n";
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        map.forEach((x,y)-> System.out.println("key : " + x + " , value : " + y));
    }
}
