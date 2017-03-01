package com.zepl.challenge.data;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.google.gson.stream.*;
import java.io.IOException;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@code TypeAdapterFactory} that handles all of the immutable types generated under {@code ToDo}.
 * @see ImmutableToDo
 */
@SuppressWarnings("all")
@Generated({"Gsons.generator", "com.zepl.challenge.data.ToDo"})
@ParametersAreNonnullByDefault
public final class GsonAdaptersToDo implements TypeAdapterFactory {
  @SuppressWarnings({"unchecked", "raw"}) // safe unchecked, types are verified in runtime
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (ToDoTypeAdapter.adapts(type)) {
      return (TypeAdapter<T>) new ToDoTypeAdapter(gson);
    }
    return null;
  }

  @Override
  public String toString() {
    return "GsonAdaptersToDo(ToDo)";
  }

  @SuppressWarnings({"unchecked", "raw"}) // safe unchecked, types are verified in runtime
  private static class ToDoTypeAdapter extends TypeAdapter<ToDo> {

    ToDoTypeAdapter(Gson gson) {
    } 

    static boolean adapts(TypeToken<?> type) {
      return ToDo.class == type.getRawType()
          || ImmutableToDo.class == type.getRawType();
    }

    @Override
    public void write(JsonWriter out, ToDo value) throws IOException {
      if (value == null) {
        out.nullValue();
      } else {
        writeToDo(out, value);
      }
    }

    @Override
    public ToDo read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        return null;
      }
      return readToDo(in);
    }

    private void writeToDo(JsonWriter out, ToDo instance)
        throws IOException {
      out.beginObject();
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

    private ToDo readToDo(JsonReader in)
        throws IOException {
      ImmutableToDo.Builder builder = ImmutableToDo.builder();
      in.beginObject();
      while (in.hasNext()) {
        eachAttribute(in, builder);
      }
      in.endObject();
      return builder.build();
    }

    private void eachAttribute(JsonReader in, ImmutableToDo.Builder builder)
        throws IOException {
      String attributeName = in.nextName();
      switch (attributeName.charAt(0)) {
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

    private void readInId(JsonReader in, ImmutableToDo.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.id(in.nextString());
      }
    }

    private void readInName(JsonReader in, ImmutableToDo.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.name(in.nextString());
      }
    }

    private void readInCreated(JsonReader in, ImmutableToDo.Builder builder)
        throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
      } else {
        builder.created(in.nextString());
      }
    }
  }
}
