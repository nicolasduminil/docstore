package fr.simplex_software.docstore.domain.codecs;

import com.mongodb.*;
import jakarta.inject.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.*;

import java.lang.reflect.*;
import java.util.*;

@Singleton
public class DocstoreDBRefCodecProvider implements CodecProvider
{
  public DocstoreDBRefCodecProvider()
  {
  }

  @Override
  public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry)
  {
    return aClass == DBRef.class ? (Codec<T>) new DocstoreDBRefCodec(codecRegistry) : null;
  }

  @Override
  public <T> Codec<T> get(Class<T> clazz, List<Type> typeArguments, CodecRegistry registry)
  {
    return CodecProvider.super.get(clazz, typeArguments, registry);
  }

  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    else
      return o != null && this.getClass() == o.getClass();
  }

  public int hashCode()
  {
    return 0;
  }

  public String toString()
  {
    return "DocstoreDBRefCodecProvider{}";
  }
}
