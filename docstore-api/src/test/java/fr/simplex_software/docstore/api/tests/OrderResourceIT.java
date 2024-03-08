package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.Order;
import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderResourceIT
{
  private static Order order;
  private static String orderId;

  @BeforeAll
  public static void beforeAll()
  {
    order = new Order ("",
      new Address ("75, rue Véronique Coulon", "Coste", "France"),
      new Address ("Wulfweg 827", "Bautzen", "Germany"));
  }

  @AfterAll
  public static void afterAll()
  {
    order = null;
  }

  @Test
  @org.junit.jupiter.api.Order(10)
  public void testCreateOrderShouldSucceed()
  {
    orderId = given()
      .header("Content-type", "application/json")
      .and().body(order)
      .when().post("/orders")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .extract().response().body().asString();
  }

  @Test
  @org.junit.jupiter.api.Order(30)
  public void testGetOrderByIdShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().queryParam("id", orderId).get("/orders/id")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().response().body()
      .jsonPath().getString("id")).isEqualTo(orderId);
  }

  @Test
  @org.junit.jupiter.api.Order(20)
  public void testUpdateOrderShouldSucceed()
  {
    order.setShippingAddress(new Address ("77, rue Véronique Coulon", "Coste", "France"));
    order.setId(orderId);
    given()
      .header("Content-type", "application/json")
      .and().body(order)
      .when().queryParam("id", orderId).put("/orders")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @org.junit.jupiter.api.Order(40)
  public void testGetSingleOrderShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().queryParam("id", orderId).get("/orders/id")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("id")).isEqualTo(orderId);
  }

  @Test
  @org.junit.jupiter.api.Order(50)
  public void testDeleteOrderShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().queryParam("id", order.getId()).delete("/orders")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }
}
