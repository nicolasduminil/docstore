package fr.simplex_software.docstore.domain.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;
import com.mongodb.*;

import java.io.*;

public class DBRefSerializer extends StdSerializer<DBRef>
{
  public DBRefSerializer()
  {
    this(null);
  }

  protected DBRefSerializer(Class<DBRef> dbrefClass)
  {
    super(dbrefClass);
  }

  @Override
  public void serialize(DBRef dbRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
  {
    if (dbRef != null)
    {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeStringField("id", (String)dbRef.getId());
      jsonGenerator.writeStringField("collectionName", dbRef.getCollectionName());
      jsonGenerator.writeStringField("databaseName", dbRef.getDatabaseName());
      jsonGenerator.writeEndObject();
    }
  }
}
