package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BuscarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaBuscarTodosUsuariosCadastrados() {
        given()
        .when()
                .get("/usuarios")
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/todos_usuarios_cadastrados.json"))
        ;
    }


    @Test
    public void testBuscarTodosUsuariosCadastradosComSucesso() {

        Response response = usuarioClient.listarUsuarios()
        .then()
                .statusCode(200)
                .extract().response()
        ;

        List<UsuarioResponse> usuarios = response.jsonPath().getList("usuarios", UsuarioResponse.class);
        int qtd = response.path("quantidade");

        Assertions.assertAll("response",
                () -> Assertions.assertNotNull(usuarios.get(0).getNome()),
                () -> Assertions.assertNotNull(usuarios.get(0).getEmail()),
                () -> Assertions.assertNotNull(usuarios.get(0).getPassword()),
                () -> Assertions.assertNotNull(usuarios.get(0).getAdministrador()),
                () -> Assertions.assertNotNull(usuarios.get(0).getId())
        );
    }

    @Test
    public void testBuscarPeloNomeUsuarioInexistente() {

        String usuarioInvalido = UsuarioDataFactory.getNomeInvalido();
        usuarioClient.listarUsuariosPorNome(usuarioInvalido)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }

    @Test
    public void testBuscarPeloEmailUsuarioInexistente() {

        String emailInvalido = UsuarioDataFactory.getEmailInvalido();
        usuarioClient.listarUsuariosPorId(emailInvalido)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}
