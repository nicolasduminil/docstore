package fr.simplex_software.docstore.domain.config;

import fr.simplex_software.docstore.domain.serializers.*;
import io.quarkus.jsonb.*;
import jakarta.inject.*;

@Singleton
public class JsonbConfig implements JsonbConfigCustomizer
{
  @Override
  public void customize(jakarta.json.bind.JsonbConfig jsonbConfig)
  {
    jsonbConfig.withDeserializers(new JsonbDBRefDeserializer());
    jsonbConfig.withSerializers(new JsonbDBRefSerializer());
  }
}
