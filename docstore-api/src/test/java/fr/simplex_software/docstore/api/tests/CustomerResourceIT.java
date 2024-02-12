package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import jakarta.mail.internet.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerResourceIT
{
  private  static Customer customer;

  @BeforeAll
  public static void beforeAll() throws AddressException
  {
    customer = new Customer("John", "Doe", new InternetAddress("john.doe@gmail.com"));
    customer.addAddress(new Address("Gebhard-Gerber-Allee 8", "Kornwestheim", "Germany"));
    customer.setId(10L);
  }

  @Test
  @Order(10)
  public void testCreateCustomerShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .and().body(customer)
      .when().post("/customer")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @Order(20)
  public void testGetCustomerShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().get("/customer")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("firstName[0]")).isEqualTo("John");
  }

  @Test
  @Order(30)
  public void testUpdateCustomerShouldSucceed()
  {
    customer.setFirstName("Jane");
    given()
      .header("Content-type", "application/json")
      .and().body(customer)
      .when().pathParam("id", customer.getId()).put("/customer/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(40)
  public void testGetSingleCustomerShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().pathParam("id", customer.getId()).get("/customer/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("firstName")).isEqualTo("Jane");
  }

  @Test
  @Order(50)
  public void testDeleteCustomerShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", customer.getId()).delete("/customer/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(60)
  public void testGetSingleCustomerShouldFail()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", customer.getId()).get("/customer/{id}")
      .then()
      .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
