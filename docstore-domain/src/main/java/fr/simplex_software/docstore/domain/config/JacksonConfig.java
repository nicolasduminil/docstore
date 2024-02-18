package fr.simplex_software.docstore.domain.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.*;
import com.mongodb.*;
import fr.simplex_software.docstore.domain.serializers.*;
import io.quarkus.jackson.*;
import jakarta.inject.*;

@Singleton
public class JacksonConfig implements ObjectMapperCustomizer
{
  @Override
  public void customize(ObjectMapper objectMapper)
  {
    SimpleModule simpleModule = new SimpleModule();
    objectMapper.registerModule(simpleModule.addDeserializer(DBRef.class, new DBRefDeserializer()));
    objectMapper.registerModule(simpleModule.addSerializer(DBRef.class, new DBRefSerializer()));
  }
}
