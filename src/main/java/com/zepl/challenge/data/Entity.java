package com.zepl.challenge.data;
import javax.annotation.Nullable;
/**
 * general properties of a task or todo
 * a todo or task has same 3 properties : id, name, created.
 */
public interface Entity {
    /**
     * unique identity of the property
     */
    @Nullable
    String id();
    /**
     * name of the property
     */
    @Nullable
    String name();
    /**
     * Timestamp when a property is created.
     */
    @Nullable
    String created();
}

