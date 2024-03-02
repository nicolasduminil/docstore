package fr.simplex_software.docstore.api.providers;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.ext.*;

public class AddressConverter implements ParamConverter<Address>
{
  @Override
  public Address fromString(String strAddress)
  {
    Address address = null;

    if (strAddress != null && !strAddress.isEmpty())
    {
      String[] adr = strAddress.split(" ");
      address = new Address(adr[0], adr[1], adr[2]);
    }
    return address;
  }

  @Override
  public String toString(Address address)
  {
    String strAddress = "";

    if (address != null)
      strAddress = address.toString();
    return strAddress;
  }
}
