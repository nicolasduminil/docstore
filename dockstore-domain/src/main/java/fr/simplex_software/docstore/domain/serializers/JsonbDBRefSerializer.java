package fr.simplex_software.docstore.domain.serializers;

import com.mongodb.*;
import jakarta.json.bind.serializer.*;
import jakarta.json.stream.*;

public class JsonbDBRefSerializer implements JsonbSerializer<DBRef>
{
  @Override
  public void serialize(DBRef dbRef, JsonGenerator jsonGenerator, SerializationContext serializationContext)
  {
    if (dbRef != null)
    {
      jsonGenerator.writeStartObject();
      jsonGenerator.write("id", (String)dbRef.getId());
      jsonGenerator.write("collectionName", dbRef.getCollectionName());
      jsonGenerator.write("databaseName", dbRef.getDatabaseName());
      jsonGenerator.writeEnd();
    }
  }
}
