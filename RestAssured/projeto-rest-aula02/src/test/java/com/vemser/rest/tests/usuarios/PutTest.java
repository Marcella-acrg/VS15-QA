package com.vemser.rest.tests.usuarios;

import com.vemser.rest.model.UsuarioRequest;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PutTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioRequest usuarioAtualizado = new UsuarioRequest();
        usuarioAtualizado.setNome("Fulano da Silva");
        usuarioAtualizado.setEmail("fulano@qa.com");
        usuarioAtualizado.setPassword("teste");
        usuarioAtualizado.setAdministrador("false");

        given()
                .pathParam("_id", idUsuario)
                .contentType(ContentType.JSON)
                .body(usuarioAtualizado)
        .when()
                .put("/usuarios/{_id}")
        .then()
                .statusCode(200)
                .assertThat()
                .body("message", equalTo("Registro alterado com sucesso"))
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDeEmailVazio() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioRequest usuarioAtualizado = new UsuarioRequest();
        usuarioAtualizado.setNome("Fulano da Silva");
        usuarioAtualizado.setEmail(" ");
        usuarioAtualizado.setPassword("teste");
        usuarioAtualizado.setAdministrador("false");

        given()
                .log().all()
                .pathParam("_id", idUsuario)
                .contentType(ContentType.JSON)
                .body(usuarioAtualizado)
        .when()
                .put("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
                .assertThat()
                .body("email", equalTo("email deve ser um email válido"))
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDePasswordVazio() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioRequest usuarioAtualizado = new UsuarioRequest();
        usuarioAtualizado.setNome("Fulano da Silva");
        usuarioAtualizado.setEmail("fulano@qa.com");
        usuarioAtualizado.setPassword(" ");
        usuarioAtualizado.setAdministrador("false");

        Response response =
        given()
                .log().all()
                .pathParam("_id", idUsuario)
                .contentType(ContentType.JSON)
                .body(usuarioAtualizado)
        .when()
                .put("/usuarios/{_id}")
        .then()
                .log().all()
                .extract().response();

        UsuarioResponse usuarioResponse = response.as(UsuarioResponse.class);

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(400, response.statusCode()),
                () -> Assertions.assertEquals("Fulano da Silva", usuarioResponse.getNome()),
                () -> Assertions.assertEquals("fulano@qa.com.br", usuarioResponse.getEmail()),
                () -> Assertions.assertEquals(" ", usuarioResponse.getPassword()),
                () -> Assertions.assertEquals("true", usuarioResponse.getAdministrador()),
                () -> Assertions.assertNotNull(usuarioResponse.getId())
        );

        String mensagemRetorno = response.jsonPath().getString("password");
        Assertions.assertEquals("password não pode ficar em branco", mensagemRetorno);
    }
}

