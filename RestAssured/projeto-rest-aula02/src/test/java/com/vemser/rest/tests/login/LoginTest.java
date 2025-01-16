package com.vemser.rest.tests.login;

import com.vemser.rest.model.LoginRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testSchemaLogarUsuario(){

        LoginRequest login = new LoginRequest();
        login.setEmail("marcella@qa.com.br");
        login.setPassword("teste");

        given()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/logar_usuario.json"))
        ;
    }

    @Test
    public void testLogarUsuario(){

        LoginRequest login = new LoginRequest();
        login.setEmail("marcella@qa.com.br");
        login.setPassword("teste");

        given()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testLogarUsuarioComCampoEmailVazio(){

        LoginRequest login = new LoginRequest();
        login.setEmail(" ");
        login.setPassword("teste");

        given()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testLogarUsuarioComCampoPasswordVazio(){

        LoginRequest login = new LoginRequest();
        login.setEmail("marcella@qa.com.br ");
        login.setPassword(" ");

        given()
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post("/login")
        .then()
                .statusCode(400)
        ;
    }
}
