package fr.simplex_software.docstore.api.providers;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.ext.*;

import java.lang.annotation.*;
import java.lang.reflect.*;

@Provider
public class AddressConverterProvider implements ParamConverterProvider
{
  private final AddressConverter converter = new AddressConverter();

  @Override
  public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations)
  {
    return aClass.equals(Address.class) ?  (ParamConverter<T>) converter : null;
  }
}
