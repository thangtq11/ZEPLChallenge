package com.zepl.challenge.data;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.google.gson.stream.*;
import java.io.IOException;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@code TypeAdapterFactory} that handles all of the immutable types generated under {@code Task}.
 * @see ImmutableTask
 */
@SuppressWarnings("all")
@Generated({"Gsons.generator", "com.zepl.challenge.data.Task"})
@ParametersAreNonnullByDefault
public final class GsonAdaptersTask implements TypeAdapterFactory {
  @SuppressWarnings({"unchecked", "raw"}) // safe unchecked, types are verified in runtime
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (TaskTypeAdapter.adapts(type)) {
      return (TypeAdapter<T>) new TaskTypeAdapter(gson);
    }
    return null;
  }

  @Override
  public String toString() {
    return "GsonAdaptersTask(Task)";
  }

  @SuppressWarnings({"unchecked", "raw"}) // safe unchecked, types are verified in runtime
  private static class TaskTypeAdapter extends TypeAdapter<Task> {
    public final TaskType statusTypeSample = null;
    private final TypeAdapter<TaskType> statusTypeAdapter;

    TaskTypeAdapter(Gson gson) {
      this.statusTypeAdapter = gson.getAdapter(TypeToken.get(TaskType.class));
    } 

    static boolean adapts(TypeToken<?> type) {
      return Task.class == type.getRawType()
          || ImmutableTask.class == type.getRawType();
    }

    @Override
    public void write(JsonWriter out, Task value) throws IOException {
      if (value == null) {
        out.nullValue();
      } else {
        writeTask(out, value);
      }
    }

    @Override
    public Task read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        return null;
      }
      return readTask(in);
    }

    private void writeTask(JsonWriter out, Task instance)
        throws IOException {
      out.beginObject();
      @Nullable String descriptionValue = instance.description();
      if (descriptionValue != null) {
        out.name("description");
        out.value(descriptionValue);
      } else if (out.getSerializeNulls()) {
        out.name("description");
        out.nullValue();
      }
      @Nullable TaskType statusValue = instance.status();
      if (statusValue != null) {
        out.name("status");
        statusTypeAdapter.write(out, statusValue);
      } else if (out.getSerializeNulls()) {
        out.name("status");
        out.nullValue();
      }
      @Nullable String idValue = instance.id();
      if (idValue != null) {
        out.name("id");
        out.value(idValue);
      } else if (out.getSerializeNulls()) {
        out.name("id");
        out.nullValue();
      }
      @Nullable String nameValue = instance.name();
      if (nameValue != null) {
        out.name("name");
        out.value(nameValue);
      } else if (out.getSerializeNulls()) {
        out.name("name");
        out.nullValue();
      }
      @Nullable String createdValue = instance.created();
      if (createdValue != null) {
        out.name("created");
        out.value(createdValue);
      } else if (out.getSerializeNulls()) {
        out.name("created");
        out.nullValue();
      }
      out.endObject();
    }

    private Task readTask(JsonReader in)
        throws IOException {
      ImmutableTask.Builder builder = ImmutableTask.builder();
      in.beginObject();
      while (in.hasNext()) {
        eachAttribute(in, builder);
      }
      in.endObject();
      return builder.build();
    }

    private void eachAttribute(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      String attributeName = in.nextName();
      switch (attributeName.charAt(0)) {
      case 'd':
        if ("description".equals(attributeName)) {
          readInDescription(in, builder);
          return;
        }
        break;
      case 's':
        if ("status".equals(attributeName)) {
          readInStatus(in, builder);
          return;
        }
        break;
      case 'i':
        if ("id".equals(attributeName)) {
          readInId(in, builder);
          return;
        }
        break;
      case 'n':
        if ("name".equals(attributeName)) {
          readInName(in, builder);
          return;
        }
        break;
      case 'c':
        if ("created".equals(attributeName)) {
          readInCreated(in, builder);
          return;
        }
        break;
      default:
      }
      in.skipValue();
    }

    private void readInDescription(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.description(in.nextString());
      }
    }

    private void readInStatus(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        TaskType value = statusTypeAdapter.read(in);
        builder.status(value);
      }
    }

    private void readInId(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.id(in.nextString());
      }
    }

    private void readInName(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.name(in.nextString());
      }
    }

    private void readInCreated(JsonReader in, ImmutableTask.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.created(in.nextString());
      }
    }
  }
}
