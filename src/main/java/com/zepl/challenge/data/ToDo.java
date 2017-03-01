package com.zepl.challenge.data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.gson.Gson;
import org.immutables.value.Value;

/**
 * A Todo is a container of a list of Tasks.
 * A Todo is constructed of the tasks that need to be completed. It is combined of following properties:
 * id : unique identity of the todo
 * name: name of the todo
 * created: time when a todo is created.
 *
 */
@Gson.TypeAdapters
@Value.Immutable
@JsonSerialize(as = ImmutableToDo.class)
@JsonDeserialize(as = ImmutableToDo.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ToDo extends Entity {
}
