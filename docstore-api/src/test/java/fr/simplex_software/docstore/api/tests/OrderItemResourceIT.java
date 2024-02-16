package fr.simplex_software.docstore.api.tests;

import com.mongodb.*;
import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.math.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemResourceIT
{
  private static OrderItem orderItem;
  private static Product product;

  @BeforeAll
  public static void beforeAll()
  {
    DBRef dbRef = new DBRef("mdb", "Products", 100);
    orderItem = new OrderItem(dbRef, BigDecimal.valueOf(549.30), 1);
    orderItem.setId(1L);
  }

  @AfterAll
  public static void afterAll()
  {
    orderItem = null;
    given().pathParam("id", product.getId()).delete("/product/{id}");
    product = null;
  }

  @Test
  @Order(5)
  public void testCreateProductShouldSucceed()
  {
    product = new Product("iPhone 9", "An apple mobile which is nothing like apple",
      BigDecimal.valueOf(549.30));
    product.setId(100L);
    given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().post("/product")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @Order(10)
  public void testCreateOrderItemShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .and().body(orderItem)
      .when().post("/order-item")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @Order(20)
  public void testGetOrderItemShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().get("/order-item")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("price[0]")).isEqualTo("549.3");
  }

  @Test
  @Order(30)
  public void testUpdateProductShouldSucceed()
  {
    orderItem.setPrice(BigDecimal.valueOf(550.00));
    given()
      .header("Content-type", "application/json")
      .and().body(orderItem)
      .when().pathParam("id", orderItem.getId()).put("/order-item/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(40)
  public void testGetSingleOrderItemShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().pathParam("id", orderItem.getId()).get("/order-item/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("price")).isEqualTo("550.0");
  }

  @Test
  @Order(45)
  public void testGetOrderItemProductShouldSucceed()
  {
    DBRef dbRef = given()
      .header("Content-type", "application/json")
      .when().pathParam("id", orderItem.getId()).get("/order-item/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getObject("product", DBRef.class);
    assertThat (given()
      .when().pathParam("id", dbRef.getId()).get("/product/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("name")).isEqualTo("iPhone 9");
  }

  @Test
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
  }
}
