package com.vemser.rest.tests.login;

import com.vemser.rest.model.LoginRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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
                .assertThat()
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
        ;
    }

    @Test
    public void testLogarUsuarioComCampoEmailVazio(){

        LoginRequest login = new LoginRequest();
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
                .assertThat()
                //.body("email", equalTo("email não pode ficar em branco"))
                .body("email", equalTo("email não pode ficar em branco"))
        ;
    }

    @Test
    public void testLogarUsuarioComCampoPasswordVazio(){

        LoginRequest login = new LoginRequest();
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
                .assertThat()
                .body("password", equalTo("password não pode ficar em branco"))
        ;
    }
}
