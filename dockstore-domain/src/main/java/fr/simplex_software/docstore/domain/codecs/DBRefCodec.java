package fr.simplex_software.docstore.domain.codecs;

import org.bson.*;
import org.bson.codecs.*;

public class DBRefCodec implements Codec
{
  @Override
  public Object decode(BsonReader bsonReader, DecoderContext decoderContext)
  {
    return null;
  }

  @Override
  public void encode(BsonWriter bsonWriter, Object o, EncoderContext encoderContext)
  {

  }

  @Override
  public Class getEncoderClass()
  {
    return null;
  }
}
