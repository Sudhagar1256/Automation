package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class ApiTest {
    private int id;

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI ="https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testCreateCategory(){
        String name = "User_"+System.currentTimeMillis();
        String image = "https://google.com";
        Map body = Map.of(
                "name",name,
                "image",image
        );
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/categories");
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name));
        id = response.jsonPath().getInt("id");
    }
    @Test(priority = 2)
    private void testGetCategory(){
        RestAssured.given()
                .pathParam("id",id)
                .when()
                .get("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", Matchers.equalTo(id));
    }
    @Test(priority = 3)
    private void testUpdateCategories(){
        Map body = Map.of(
                "name","sudha",
                "image","https://google/lion.com"
        );
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .when()
                .put("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", Matchers.equalTo(id));
    }
}