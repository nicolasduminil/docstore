package fr.simplex_software.docstore.api.tests;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.test.junit.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.math.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductResourceIT
{
  private static Product product;
  private static String productId;

  @BeforeAll
  public static void beforeAll()
  {
    product = new Product("iPhone 9", "An apple mobile which is nothing like apple",
      BigDecimal.valueOf(549.30));
  }

  @AfterAll
  public static void afterAll()
  {
    product = null;
  }

  @Test
  @Order(10)
  public void testCreateProductShouldSucceed()
  {
    productId = given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().post("/products")
      .then()
      .statusCode(HttpStatus.SC_ACCEPTED)
      .extract().response().body().asString();
  }

  @Test
  @Order(20)
  public void testGetProductByIdShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().queryParam("id", productId).get("/products/id")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("name")).isEqualTo("iPhone 9");
  }

  @Test
  @Order(30)
  public void testUpdateProductShouldSucceed()
  {
    product.setName("iPhone 10");
    product.setId(productId);
    assertDoesNotThrow(() -> given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().put("/products")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT));
  }

  @Test
  @Order(50)
  public void testDeleteProductShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().queryParam("id", product.getId()).delete("/products")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }
}
