package com.vemser.rest.tests;

import com.vemser.rest.model.LoginModel;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class LoginTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testLogarUsuario(){

        LoginModel login = new LoginModel();
        login.setEmail("marcella@qa.com.br");
        login.setPassword("teste");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void testLogarUsuarioComCampoEmailVazio(){

        LoginModel login = new LoginModel();
        login.setEmail(" ");
        login.setPassword("teste");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testLogarUsuarioComCampoPasswordVazio(){

        LoginModel login = new LoginModel();
        login.setEmail("marcella@qa.com.br ");
        login.setPassword(" ");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }
}
