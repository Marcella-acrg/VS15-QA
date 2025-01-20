package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioRequest;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class AtualizarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaAtualizarUsuario() {

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
                .body(matchesJsonSchemaInClasspath("schemas/atualizar_usuario.json"))
        ;
    }


    @Test
    public void testAtualizarUsuarioComSucesso() {

        Object[] usuarioValido = UsuarioDataFactory.atualizarUsuarioRetornarID();
        UsuarioRequest usuario = (UsuarioRequest)usuarioValido[0];
        String id = (String)usuarioValido[1];
        System.out.println(id);
        UsuarioResponse response = usuarioClient.atualizarUsuariosPorId(usuario, id)
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"))
                .extract().as(UsuarioResponse.class)
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDeEmailVazio() {

        Object[] usuarioValido = UsuarioDataFactory.atualizarUsuarioComEmailVazio();
        UsuarioRequest usuario = (UsuarioRequest)usuarioValido[0];
        String id = (String)usuarioValido[1];
        System.out.println(id);
        UsuarioResponse response = usuarioClient.atualizarUsuariosPorId(usuario, id)
        .then()
                .assertThat()
                .statusCode(400)
                .body("email", equalTo("email deve ser um email válido"))
                .extract().as(UsuarioResponse.class)
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDePasswordVazio() {

        Object[] usuarioValido = UsuarioDataFactory.atualizarUsuarioComPasswordVazio();
        UsuarioRequest usuario = (UsuarioRequest)usuarioValido[0];
        String id = (String)usuarioValido[1];
        System.out.println(id);
        UsuarioResponse response = usuarioClient.atualizarUsuariosPorId(usuario, id)
        .then()
                .assertThat()
                .statusCode(400)
                .body("password", equalTo("password não pode ficar em branco"))
                .extract().as(UsuarioResponse.class)
        ;
    }
}

