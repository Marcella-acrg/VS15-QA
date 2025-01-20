package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioRequest;
import com.vemser.rest.model.UsuarioResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BuscarUsuariosPorIdTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaBuscarUsuarioPorIDValido() {

        String idUsuario = "xURzlrN6Pbd6iI31";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
        ;
    }


    @Test
    public void testBuscarUsuarioPorIDValidoComSucesso() {

        Object[] usuarioValido = UsuarioDataFactory.cadastrarUsuarioRetornarID();
        UsuarioRequest usuario = (UsuarioRequest)usuarioValido[0];
        String id = (String)usuarioValido[1];

        UsuarioResponse response = usuarioClient.listarUsuariosPorId(id)
        .then()
                .statusCode(200)
                .extract().as(UsuarioResponse.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(usuario.getNome(), response.getNome()),
                () -> Assertions.assertEquals(usuario.getEmail(), response.getEmail()),
                () -> Assertions.assertEquals(usuario.getPassword(), response.getPassword()),
                () -> Assertions.assertEquals(usuario.getAdministrador(), response.getAdministrador()),
                () -> Assertions.assertNotNull(response.getId())
        );
    }

    @Test
    public void testBuscarUsuarioPorIDInexistente() {

       String idInvalido = UsuarioDataFactory.getIdInvalido();
       usuarioClient.listarUsuariosPorId(idInvalido)
       .then()
               .assertThat()
               .statusCode(400)
               .body("message", equalTo("Usuário não encontrado"))
        ;
    }

    @Test
    public void testBuscarUsuarioPorIDComCampoVazio() {
        String getIdVazio = UsuarioDataFactory.getIdVazio();
        usuarioClient.listarUsuariosPorId(getIdVazio)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}
