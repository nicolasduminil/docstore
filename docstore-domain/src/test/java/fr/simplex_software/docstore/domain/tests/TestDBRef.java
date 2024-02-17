package fr.simplex_software.docstore.domain.tests;

import com.mongodb.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class TestDBRef
{
  @Test
  public void testDbRef()
  {
    DBRef dbRef = new DBRef("databaseName", "collectionName", "id");
    assertThat(dbRef).isNotNull();
    assertThat(dbRef.getId()).isExactlyInstanceOf(String.class);
  }
}
