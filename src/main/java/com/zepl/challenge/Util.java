package com.zepl.challenge;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zepl.challenge.data.Entity;
import com.zepl.challenge.data.Task;
import com.zepl.challenge.data.ToDo;
import com.zepl.challenge.rest.ToDoRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by THANGTQ
 *
 */
public class Util {
    private final static Logger log = LoggerFactory.getLogger(Util.class);
    public static boolean isValidJSon(String jsonString)
    {
        Gson gson = new Gson();
        boolean result=false;
        try{
            Map<String, String> map = gson.fromJson(jsonString, new TypeToken<Map<String, String>>() {
            }.getType());
            Stream stream=map.entrySet().stream().filter(mp->(mp.getKey()==null||mp.getKey().isEmpty()||mp.getValue()==null||mp.getValue().isEmpty()));
            if(stream.count()>0) result=false;
            else result=true;
            return result;
        }catch(Exception ex){
        }
        return result;
    }


}
