package com.zepl.challenge.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Immutable implementation of {@link Task}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableTask.builder()}.
 */
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@Generated({"Immutables.generator", "Task"})
@Immutable
public final class ImmutableTask implements Task {
  private final @Nullable String description;
  private final @Nullable TaskType status;
  private final @Nullable String id;
  private final @Nullable String name;
  private final @Nullable String created;

  private ImmutableTask(
      @Nullable String description,
      @Nullable TaskType status,
      @Nullable String id,
      @Nullable String name,
      @Nullable String created) {
    this.description = description;
    this.status = status;
    this.id = id;
    this.name = name;
    this.created = created;
  }

  /**
   * description of the task
   */
  @JsonProperty("description")
  @Override
  public @Nullable String description() {
    return description;
  }

  /**
   * status is DONE when the task is completed or NOT_DONE vice versa
   */
  @JsonProperty("status")
  @Override
  public @Nullable TaskType status() {
    return status;
  }

  /**
   * unique identity of the property
   */
  @JsonProperty("id")
  @Override
  public @Nullable String id() {
    return id;
  }

  /**
   * name of the property
   */
  @JsonProperty("name")
  @Override
  public @Nullable String name() {
    return name;
  }

  /**
   * Timestamp when a property is created.
   */
  @JsonProperty("created")
  @Override
  public @Nullable String created() {
    return created;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Task#description() description} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for description (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTask withDescription(@Nullable String value) {
    if (Objects.equals(this.description, value)) return this;
    return new ImmutableTask(value, this.status, this.id, this.name, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Task#status() status} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for status (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTask withStatus(@Nullable TaskType value) {
    if (this.status == value) return this;
    return new ImmutableTask(this.description, value, this.id, this.name, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Task#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTask withId(@Nullable String value) {
    if (Objects.equals(this.id, value)) return this;
    return new ImmutableTask(this.description, this.status, value, this.name, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Task#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTask withName(@Nullable String value) {
    if (Objects.equals(this.name, value)) return this;
    return new ImmutableTask(this.description, this.status, this.id, value, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link Task#created() created} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for created (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableTask withCreated(@Nullable String value) {
    if (Objects.equals(this.created, value)) return this;
    return new ImmutableTask(this.description, this.status, this.id, this.name, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableTask} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableTask
        && equalTo((ImmutableTask) another);
  }

  private boolean equalTo(ImmutableTask another) {
    return Objects.equals(description, another.description)
        && Objects.equals(status, another.status)
        && Objects.equals(id, another.id)
        && Objects.equals(name, another.name)
        && Objects.equals(created, another.created);
  }

  /**
   * Computes a hash code from attributes: {@code description}, {@code status}, {@code id}, {@code name}, {@code created}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + Objects.hashCode(description);
    h = h * 17 + Objects.hashCode(status);
    h = h * 17 + Objects.hashCode(id);
    h = h * 17 + Objects.hashCode(name);
    h = h * 17 + Objects.hashCode(created);
    return h;
  }

  /**
   * Prints the immutable value {@code Task} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "Task{"
        + "description=" + description
        + ", status=" + status
        + ", id=" + id
        + ", name=" + name
        + ", created=" + created
        + "}";
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json implements Task {
    @Nullable String description;
    @Nullable TaskType status;
    @Nullable String id;
    @Nullable String name;
    @Nullable String created;
    @JsonProperty("description")
    public void setDescription(@Nullable String description) {
      this.description = description;
    }
    @JsonProperty("status")
    public void setStatus(@Nullable TaskType status) {
      this.status = status;
    }
    @JsonProperty("id")
    public void setId(@Nullable String id) {
      this.id = id;
    }
    @JsonProperty("name")
    public void setName(@Nullable String name) {
      this.name = name;
    }
    @JsonProperty("created")
    public void setCreated(@Nullable String created) {
      this.created = created;
    }
    @Override
    public String description() { throw new UnsupportedOperationException(); }
    @Override
    public TaskType status() { throw new UnsupportedOperationException(); }
    @Override
    public String id() { throw new UnsupportedOperationException(); }
    @Override
    public String name() { throw new UnsupportedOperationException(); }
    @Override
    public String created() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static ImmutableTask fromJson(Json json) {
    ImmutableTask.Builder builder = ImmutableTask.builder();
    if (json.description != null) {
      builder.description(json.description);
    }
    if (json.status != null) {
      builder.status(json.status);
    }
    if (json.id != null) {
      builder.id(json.id);
    }
    if (json.name != null) {
      builder.name(json.name);
    }
    if (json.created != null) {
      builder.created(json.created);
    }
    return builder.build();
  }

  /**
   * Creates an immutable copy of a {@link Task} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Task instance
   */
  public static ImmutableTask copyOf(Task instance) {
    if (instance instanceof ImmutableTask) {
      return (ImmutableTask) instance;
    }
    return ImmutableTask.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableTask ImmutableTask}.
   * @return A new ImmutableTask builder
   */
  public static ImmutableTask.Builder builder() {
    return new ImmutableTask.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableTask ImmutableTask}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @NotThreadSafe
  public static final class Builder {
    private @Nullable String description;
    private @Nullable TaskType status;
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String created;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.zepl.challenge.data.Entity} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Entity instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.zepl.challenge.data.Task} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(Task instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof Entity) {
        Entity instance = (Entity) object;
        @Nullable String nameValue = instance.name();
        if (nameValue != null) {
          name(nameValue);
        }
        @Nullable String createdValue = instance.created();
        if (createdValue != null) {
          created(createdValue);
        }
        @Nullable String idValue = instance.id();
        if (idValue != null) {
          id(idValue);
        }
      }
      if (object instanceof Task) {
        Task instance = (Task) object;
        @Nullable String descriptionValue = instance.description();
        if (descriptionValue != null) {
          description(descriptionValue);
        }
        @Nullable TaskType statusValue = instance.status();
        if (statusValue != null) {
          status(statusValue);
        }
      }
    }

    /**
     * Initializes the value for the {@link Task#description() description} attribute.
     * @param description The value for description (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("description")
    public final Builder description(@Nullable String description) {
      this.description = description;
      return this;
    }

    /**
     * Initializes the value for the {@link Task#status() status} attribute.
     * @param status The value for status (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("status")
    public final Builder status(@Nullable TaskType status) {
      this.status = status;
      return this;
    }

    /**
     * Initializes the value for the {@link Task#id() id} attribute.
     * @param id The value for id (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("id")
    public final Builder id(@Nullable String id) {
      this.id = id;
      return this;
    }

    /**
     * Initializes the value for the {@link Task#name() name} attribute.
     * @param name The value for name (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("name")
    public final Builder name(@Nullable String name) {
      this.name = name;
      return this;
    }

    /**
     * Initializes the value for the {@link Task#created() created} attribute.
     * @param created The value for created (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("created")
    public final Builder created(@Nullable String created) {
      this.created = created;
      return this;
    }

    /**
     * Builds a new {@link ImmutableTask ImmutableTask}.
     * @return An immutable instance of Task
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableTask build() {
      return new ImmutableTask(description, status, id, name, created);
    }
  }
}
