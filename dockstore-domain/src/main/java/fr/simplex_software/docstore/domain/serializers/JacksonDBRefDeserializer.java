package fr.simplex_software.docstore.domain.serializers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.mongodb.*;

import java.io.*;

public class JacksonDBRefDeserializer extends StdDeserializer<DBRef>
{
  public JacksonDBRefDeserializer()
  {
    this(null);
  }

  public JacksonDBRefDeserializer(Class<DBRef> dbrefClass)
  {
    super(dbrefClass);
  }

  @Override
  public DBRef deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException
  {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    return new DBRef(node.findValue("databaseName").asText(), node.findValue("collectionName").asText(), node.findValue("id").asText());
  }
}
