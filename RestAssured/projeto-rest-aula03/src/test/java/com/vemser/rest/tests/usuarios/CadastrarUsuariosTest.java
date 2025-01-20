package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CadastrarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaCadastrarUsuario(){

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Marcella Araújo");
        usuario.setEmail("marcella@qa.com.br");
        usuario.setPassword("teste");
        usuario.setAdministrador("true");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/cadastrar_usuario.json"))
        ;
    }


    @Test
    public void testCadastrarUsuarioComDadosValidos(){

        UsuarioRequest usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
        .then()
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
        ;
    }

    @Test
    public void testTentarCadastrarUsuarioComEmailVazio(){

        UsuarioRequest usuarioSemEmail = UsuarioDataFactory.usuarioSemEmail();

        usuarioClient.cadastrarUsuarios(usuarioSemEmail)
        .then()
                .assertThat()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"))
        ;
    }

    @Test
    public void testCadastrarUsuarioComEmailExistente(){

        UsuarioRequest usuarioComEmailExistente = UsuarioDataFactory.usuarioComEmailExistente();

        usuarioClient.cadastrarUsuarios(usuarioComEmailExistente)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"))
        ;
    }
}
