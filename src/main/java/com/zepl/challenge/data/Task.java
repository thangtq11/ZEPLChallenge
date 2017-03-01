package com.zepl.challenge.data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.gson.Gson;
import org.immutables.value.Value;
import javax.annotation.Nullable;

/**
 * A task it is an assignment that needs to be accomplished within a defined period of time or by a deadline. It is combined of following properties:
 * id : unique identity of the task
 * name: name of the task
 * description: Description of the task
 * status: DONE or NOT_DONE
 * created: time when a task is created.
 *
 */
@Gson.TypeAdapters
@Value.Immutable
@JsonSerialize(as = ImmutableTask.class)
@JsonDeserialize(as = ImmutableTask.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Task extends Entity {
    /**
     * description of the task
     */
    @Nullable
    String description();
    /**
     * status is DONE when the task is completed or NOT_DONE vice versa
     */
    @Nullable
    TaskType status();
}
