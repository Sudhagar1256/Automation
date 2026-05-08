package com.krce;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class Fake1Test {
    private String name;
    private String email;
    private String password;
    @BeforeClass
    public void setUp()
    {
        baseURI = "https://api.escuelajs.co/api/v1";
    }
    @Test(priority=1)
    public void testRegister(){
        name = "user_"+System.currentTimeMillis();
        email = name + "@email.com";
        password = "123456789";
        String avatar = "https://i.imgur.com/LDOO4Qs.jpg";
        Map body = Map.of(
                "name",name,
                "email",email,
                "password",password,
                "avatar",avatar

        );
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post ("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue());
    }
    @Test(priority = 2)
    public void testLogin(){
        Map body = Map.of(
                "email",email,
                "password",password
        );
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post ("/auth/login")
                .then()
                .log().all()
                .statusCode(201)
                .body("access_token", Matchers.notNullValue())
                .body("refresh_token", Matchers.notNullValue());

    }
}