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
 * Immutable implementation of {@link ToDo}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableToDo.builder()}.
 */
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@Generated({"Immutables.generator", "ToDo"})
@Immutable
public final class ImmutableToDo implements ToDo {
  private final @Nullable String id;
  private final @Nullable String name;
  private final @Nullable String created;

  private ImmutableToDo(
      @Nullable String id,
      @Nullable String name,
      @Nullable String created) {
    this.id = id;
    this.name = name;
    this.created = created;
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
   * Copy the current immutable object by setting a value for the {@link ToDo#id() id} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableToDo withId(@Nullable String value) {
    if (Objects.equals(this.id, value)) return this;
    return new ImmutableToDo(value, this.name, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ToDo#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableToDo withName(@Nullable String value) {
    if (Objects.equals(this.name, value)) return this;
    return new ImmutableToDo(this.id, value, this.created);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link ToDo#created() created} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for created (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableToDo withCreated(@Nullable String value) {
    if (Objects.equals(this.created, value)) return this;
    return new ImmutableToDo(this.id, this.name, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableToDo} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableToDo
        && equalTo((ImmutableToDo) another);
  }

  private boolean equalTo(ImmutableToDo another) {
    return Objects.equals(id, another.id)
        && Objects.equals(name, another.name)
        && Objects.equals(created, another.created);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code created}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + Objects.hashCode(id);
    h = h * 17 + Objects.hashCode(name);
    h = h * 17 + Objects.hashCode(created);
    return h;
  }

  /**
   * Prints the immutable value {@code ToDo} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "ToDo{"
        + "id=" + id
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
  static final class Json implements ToDo {
    @Nullable String id;
    @Nullable String name;
    @Nullable String created;
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
  static ImmutableToDo fromJson(Json json) {
    ImmutableToDo.Builder builder = ImmutableToDo.builder();
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
   * Creates an immutable copy of a {@link ToDo} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable ToDo instance
   */
  public static ImmutableToDo copyOf(ToDo instance) {
    if (instance instanceof ImmutableToDo) {
      return (ImmutableToDo) instance;
    }
    return ImmutableToDo.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableToDo ImmutableToDo}.
   * @return A new ImmutableToDo builder
   */
  public static ImmutableToDo.Builder builder() {
    return new ImmutableToDo.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableToDo ImmutableToDo}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @NotThreadSafe
  public static final class Builder {
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
     * Fill a builder with attribute values from the provided {@code com.zepl.challenge.data.ToDo} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ToDo instance) {
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
    }

    /**
     * Initializes the value for the {@link ToDo#id() id} attribute.
     * @param id The value for id (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("id")
    public final Builder id(@Nullable String id) {
      this.id = id;
      return this;
    }

    /**
     * Initializes the value for the {@link ToDo#name() name} attribute.
     * @param name The value for name (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("name")
    public final Builder name(@Nullable String name) {
      this.name = name;
      return this;
    }

    /**
     * Initializes the value for the {@link ToDo#created() created} attribute.
     * @param created The value for created (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    @JsonProperty("created")
    public final Builder created(@Nullable String created) {
      this.created = created;
      return this;
    }

    /**
     * Builds a new {@link ImmutableToDo ImmutableToDo}.
     * @return An immutable instance of ToDo
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableToDo build() {
      return new ImmutableToDo(id, name, created);
    }
  }
}
