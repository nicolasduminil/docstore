package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import io.restassured.response.*;
import io.restassured.specification.*;
import jakarta.mail.internet.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerResourceIT
{
  private static Customer customer;
  private static String customerId;

  @BeforeAll
  public static void beforeAll() throws AddressException
  {
    customer = new Customer("John", "Doe", new InternetAddress("john.doe@gmail.com"));
    customer.addAddress(new Address("Gebhard-Gerber-Allee 8", "Kornwestheim", "Germany"));
  }

  @Test
  @Order(10)
  public void testCreateCustomerShouldSucceed()
  {
    customerId = given()
      .header("Content-type", "application/json")
      .and().body(customer)
      .when().post("/customers")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .extract().response().body().asString();
  }

  @Test
  @Order(20)
  public void testGetCustomerByIdShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().queryParam("id", customerId).get("/customers/id")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("firstName")).isEqualTo("John");
  }

  @Test
  @Order(30)
  public void testUpdateCustomerShouldSucceed()
  {
    customer.setFirstName("Jane");
    customer.setId(customerId);
    assertDoesNotThrow(() -> given()
      .header("Content-type", "application/json")
      .and().body(customer)
      .when().put("/customers")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT));
  }

  @Test
  @Order(50)
  public void testDeleteCustomerByIdShouldSucceed()
  {
    assertDoesNotThrow (() -> given()
      .header("Content-type", "application/json")
      .when().queryParam("id", customerId).delete("/customers")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT));
  }
}
