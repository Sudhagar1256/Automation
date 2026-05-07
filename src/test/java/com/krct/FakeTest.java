package com.krct;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;



public class FakeTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test
    public void testGet() {

        RestAssured.given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0));


    }

    @Test
    public void testGetByPrice() {

        RestAssured.given()
                .queryParam("price", 100)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("[0].price",Matchers.equalTo(100));



    }

    @Test
    public void testGetCAteogires(){
        RestAssured.given()
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("$",Matchers.instanceOf(List.class));
    }


    @Test
    public void testTitle() {

        RestAssured.given()
                .queryParam("title", "Generic")
                .when()
                .get("/products")
                .then()
                .statusCode(200);


    }

    @Test
    public void testGetCategoryById(){
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .get("/categories/{id}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1));
    }

    @Test
    public void testDelete(){
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .delete("/categories/{id}")
                .then()
                .statusCode(200);
    }


}
