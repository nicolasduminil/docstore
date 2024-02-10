package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import jakarta.mail.internet.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusTest
public class CustomerResourceTest
{
  private  static Customer customer;

  @BeforeAll
  public static void beforeAll() throws AddressException
  {
    customer = new Customer("John", "Doe", new InternetAddress("john.doe@gmail.com"));
    customer.addAddress(new Address("Gebhard-Gerber-Allee 8", "Kornwestheim", "Germany"));
  }

  @Test
  public void testCreateCustomerShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .and().body(customer)
      .when().post("/customer")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  /*@Test
  public void testGetCustomerShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().get("/customer")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().response().jsonPath().getString())
  }*/
}
