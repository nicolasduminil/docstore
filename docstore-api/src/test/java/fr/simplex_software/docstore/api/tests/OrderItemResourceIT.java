package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import org.apache.http.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.*;

import java.math.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemResourceIT
{
  private static OrderItem orderItem;
  private static Product product;
  private static String productId;
  private static String orderItemId;

  @BeforeAll
  public static void beforeAll()
  {
    product = new Product("iPhone 9", "An apple mobile which is nothing like apple",
      BigDecimal.valueOf(549.30));
    productId = given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().post("/products")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .extract().response().body().asString();
    orderItem = new OrderItem(productId, BigDecimal.valueOf(549.30), 1);
  }

  @AfterAll
  public static void afterAll()
  {
    orderItem = null;
    product = null;
  }

  @Test
  @Order(10)
  public void testCreateOrderItemShouldSucceed()
  {
    orderItemId = given()
      .header("Content-type", "application/json")
      .and().body(orderItem)
      .when().post("/order-items")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .extract().response().body().asString();
  }

  @Test
  @Order(20)
  public void testGetOrderItemShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().queryParam("id", orderItemId).get("/order-items/id")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("price")).isEqualTo("549.3");
  }

  @Test
  @Order(30)
  public void testUpdateProductShouldSucceed()
  {
    orderItem.setPrice(BigDecimal.valueOf(550.00));
    orderItem.setId(orderItemId);
    given()
      .header("Content-type", "application/json")
      .and().body(orderItem)
      .when().put("/order-items")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  /*@Test
  @Order(50)
  public void testDeleteOrderItemShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", orderItem.getId()).delete("/order-item/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(60)
  public void testGetSingleOrderItemShouldFail()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", orderItem.getId()).get("/order-item/{id}")
      .then()
      .statusCode(HttpStatus.SC_NOT_FOUND);
  }*/
}
