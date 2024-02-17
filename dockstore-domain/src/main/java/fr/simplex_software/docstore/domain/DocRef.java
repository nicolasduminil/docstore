package fr.simplex_software.docstore.domain;

import com.mongodb.*;

public class DocRef extends DBRef
{
  public DocRef()
  {
    this("$ref", 0);
  }

  public DocRef(String collectionName, Object id)
  {
    super(collectionName, id);
  }

  public DocRef(String databaseName, String collectionName, Object id)
  {
    super(databaseName, collectionName, id);
  }

  @Override
  public Object getId()
  {
    return super.getId();
  }

  @Override
  public String getCollectionName()
  {
    return super.getCollectionName();
  }

  @Override
  public String getDatabaseName()
  {
    return super.getDatabaseName();
  }

  @Override
  public boolean equals(Object o)
  {
    return super.equals(o);
  }

  @Override
  public int hashCode()
  {
    return super.hashCode();
  }

  @Override
  public String toString()
  {
    return super.toString();
  }
}
