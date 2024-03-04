package fr.simplex_software.docstore.api.tests;

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
public class ProductResource
{
  private static Product product;

  @BeforeAll
  public static void beforeAll()
  {
    product = new Product("iPhone 9", "An apple mobile which is nothing like apple",
      BigDecimal.valueOf(549.30));
    product.setId("100L");
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
    given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().post("/product")
      .then()
      .statusCode(HttpStatus.SC_CREATED);
  }

  @Test
  @Order(20)
  public void testGetProductShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().get("/product")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("name[0]")).isEqualTo("iPhone 9");
  }

  @Test
  @Order(30)
  public void testUpdateProductShouldSucceed()
  {
    product.setName("iPhone 10");
    given()
      .header("Content-type", "application/json")
      .and().body(product)
      .when().pathParam("id", product.getId()).put("/product/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(40)
  public void testGetSingleProductShouldSucceed()
  {
    assertThat (given()
      .header("Content-type", "application/json")
      .when().pathParam("id", product.getId()).get("/product/{id}")
      .then()
      .statusCode(HttpStatus.SC_OK)
      .extract().body().jsonPath().getString("name")).isEqualTo("iPhone 10");
  }

  @Test
  @Order(50)
  public void testDeleteProductShouldSucceed()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", product.getId()).delete("/product/{id}")
      .then()
      .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  @Order(60)
  public void testGetSingleProductShouldFail()
  {
    given()
      .header("Content-type", "application/json")
      .when().pathParam("id", product.getId()).get("/product/{id}")
      .then()
      .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
