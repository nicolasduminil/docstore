package fr.simplex_software.docstore.domain.codecs;

import com.mongodb.*;
import com.mongodb.assertions.*;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.*;

public class DocstoreDBRefCodec implements Codec<DBRef>
{
  private final CodecRegistry registry;

  public DocstoreDBRefCodec(CodecRegistry registry)
  {
    this.registry = (CodecRegistry) Assertions.notNull("registry", registry);
  }

  public void encode(BsonWriter writer, DBRef value, EncoderContext encoderContext)
  {
    writer.writeStartDocument();
    writer.writeString("$ref", value.getCollectionName());
    writer.writeName("$id");
    Codec codec = this.registry.get(value.getId().getClass());
    codec.encode(writer, value.getId(), encoderContext);
    if (value.getDatabaseName() != null)
      writer.writeString("$db", value.getDatabaseName());
    writer.writeEndDocument();
  }

  public Class<DBRef> getEncoderClass()
  {
    return DBRef.class;
  }

  public DBRef decode(BsonReader reader, DecoderContext decoderContext)
  {
    reader.readStartDocument();
    DBRef dbRef = new DBRef(reader.readString("$ref"), reader.readString("$id"), reader.readString("$db"));
    reader.readEndDocument();
    return dbRef;
  }
}
