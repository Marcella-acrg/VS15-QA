package com.vemser.rest.tests.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class DeleteTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testExcluirUsuarioSemCarrinhoComSucesso() {

        String idUsuario = "CIOFDNvz3BWwsnOt";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testExcluirUsuarioComIDInvalido() {

        String idUsuario = "ZzzzPY0cbmQhpEz1";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Nenhum registro excluido"))
        ;
    }


@Test
    public void testExcluirUsuarioComCampoDeIDVazio() {

        String idUsuario = " ";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Nenhum registro excluido"))
        ;
    }
}



