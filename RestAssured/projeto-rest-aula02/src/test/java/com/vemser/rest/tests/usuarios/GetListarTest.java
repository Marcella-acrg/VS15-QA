package com.vemser.rest.tests.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetListarTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }


    @Test
    public void testBuscarUsuarioPorIDValidoComSucessoHamcrest() {

        String idUsuario = "xURzlrN6Pbd6iI31";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("nome", equalTo("Marcella Araújo"))
                .body("email", containsStringIgnoringCase("marcella@qa.com.br"))
                .body("password", containsStringIgnoringCase("teste"))
                .body("administrador", containsStringIgnoringCase("true"))
                .body("_id", equalTo(idUsuario));
    }
}
