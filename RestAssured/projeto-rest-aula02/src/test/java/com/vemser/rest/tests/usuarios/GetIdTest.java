package com.vemser.rest.tests.usuarios;

import com.vemser.rest.model.UsuarioResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class GetIdTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testBuscarUsuarioPorIDValidoComSucessoSoftAssertions() {

        String idUsuario = "xURzlrN6Pbd6iI31";

        UsuarioResponse response =
                given()
                        .log().all()
                        .pathParam("_id", idUsuario)
                .when()
                        .get("/usuarios/{_id}")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(UsuarioResponse.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals("Marcella Araújo", response.getNome()),
                () -> Assertions.assertEquals("marcella@qa.com.br", response.getEmail()),
                () -> Assertions.assertEquals("teste", response.getPassword()),
                () -> Assertions.assertEquals("true", response.getAdministrador()),
                () -> Assertions.assertNotNull(response.getId())
        );
    }

    @Test
    public void testBuscarUsuarioPorIDInexistente() {

        String idUsuario = "zZZzzzN6Pbd6iI31";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }

    @Test
    public void testBuscarUsuarioPorIDIComCampoVazio() {

        String idUsuario = " ";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}
