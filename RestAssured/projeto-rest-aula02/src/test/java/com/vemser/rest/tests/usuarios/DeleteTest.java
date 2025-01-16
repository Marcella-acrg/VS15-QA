package com.vemser.rest.tests.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class DeleteTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
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
                .body("message", equalTo("Nenhum registro excluido"));
    }
}
