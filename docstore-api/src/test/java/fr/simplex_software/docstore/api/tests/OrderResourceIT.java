package fr.simplex_software.docstore.api.tests;

import com.mongodb.*;
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

  @BeforeAll
  public static void beforeAll()
  {
    order = new Order (new DBRef("mdb", "Customers", 10),
      new Address ("75, rue Véronique Coulon", "Coste", "France"),
      new Address ("Wulfweg 827", "Bautzen", "Germany"));
    order.setId(1000l);
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
    given()
      .header("Content-type", "application/json")
      .and().body(order)
      .when().post("/order")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @org.junit.jupiter.api.Order(20)
  public void testGetOrderShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().get("/order")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().response().body()
      .jsonPath().getString("id[0]")).isEqualTo("1000");
  }

  @Test
  @org.junit.jupiter.api.Order(30)
  public void testUpdateOrderShouldSucceed()
  {
    order.setShippingAddress(new Address ("77, rue Véronique Coulon", "Coste", "France"));
    given()
      .header("Content-type", "application/json")
      .and().body(order)
      .when().pathParam("id", order.getId()).put("/order/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @org.junit.jupiter.api.Order(40)
  public void testGetSingleOrderShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().pathParam("id", order.getId()).get("/order/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("id")).isEqualTo("1000");
  }

  @Test
  @org.junit.jupiter.api.Order(50)
  public void testDeleteOrderShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", order.getId()).delete("/order/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @org.junit.jupiter.api.Order(60)
  public void testGetSingleOrderItemShouldFail()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", order.getId()).get("/order/{id}")
      .then()
      .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
