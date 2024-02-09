package fr.simplex_software.docstore.domain.serializers;

import com.mongodb.*;
import jakarta.json.*;
import jakarta.json.bind.serializer.*;
import jakarta.json.stream.*;

import java.lang.reflect.*;

public class JsonbDBRefDeserializer implements JsonbDeserializer<DBRef>
{
  @Override
  public DBRef deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type)
  {
    JsonObject node = jsonParser.getObject();
    return new DBRef(node.getString("databaseName"),node.getString("collectionName"), node.getString("id"));
  }
}
