package com.vemser.rest.tests;

import com.vemser.rest.model.UsuarioModel;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class UsuariosTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testBuscarTodosUsuariosCadastradosComSucesso() {

        given()
        .when()
                .get("/usuarios")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testBuscarPeloNomeUsuarioInexistente() {

        String nomeUsuarioInexistente = "Gilma Araújo";

        given()
                .log().all()
                .pathParam("nome", nomeUsuarioInexistente)
        .when()
                .get("/usuarios/{nome}")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testBuscarPeloEmailUsuarioInexistente() {

        String emailUsuarioInexistente = "gilma@qa.com.br";

        given()
                .log().all()
                .pathParam("email", emailUsuarioInexistente)
        .when()
                .get("/usuarios/{email}")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testBuscarUsuarioPorIDValidoComSucesso() {

        String idUsuario = "xURzlrN6Pbd6iI31";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testBuscarUsuarioPorIDInexistente() {

        String idUsuario = "zZZzzzN6Pbd6iI31";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testBuscarUsuarioPorIDIComCampoVazio() {

        String idUsuario = " ";

        given()
                .log().all()
                .pathParam("_id", idUsuario)
        .when()
                .get("/usuarios/{_id}")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testListarUsuarioPorNomeComSucesso() {

        String nome = "Marcella Araújo";

        given()
                .queryParam("nome", nome)
        .when()
                .get("/usuarios")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    public void testCadastrarUsuarioComSucesso(){

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Marcella Araújo");
        usuario.setEmail("marcella@qa.com.br");
        usuario.setPassword("teste");
        usuario.setAdministrador("true");

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .statusCode(201)
        ;
    }

    @Test
    public void testCadastrarUsuarioComEmailVazio(){

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("QA 123");
        usuario.setEmail(" ");
        usuario.setPassword("qa123");
        usuario.setAdministrador("true");

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testCadastrarUsuarioComEmailExistente(){

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Marcella Coelho");
        usuario.setEmail("marcella@qa.com.br ");
        usuario.setPassword("qa123");
        usuario.setAdministrador("false");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .log().all()
                .statusCode(400)
        ;
    }

    @Test
    public void testAtualizarUsuarioComSucesso() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioModel usuarioAtualizado = new UsuarioModel();
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
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDeEmailVazio() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Fulano da Silva");
        usuarioAtualizado.setEmail(" ");
        usuarioAtualizado.setPassword("teste");
        usuarioAtualizado.setAdministrador("false");

        given()
                .pathParam("_id", idUsuario)
                .contentType(ContentType.JSON)
                .body(usuarioAtualizado)
        .when()
                .put("/usuarios/{_id}")
        .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testAtualizarUsuarioComCampoDePasswordVazio() {

        String idUsuario = "0uxuPY0cbmQhpEz1";

        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setNome("Fulano da Silva");
        usuarioAtualizado.setEmail("fulano@qa.com");
        usuarioAtualizado.setPassword(" ");
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
                .statusCode(400) //
        ;
    }

    @Test
    public void testExcluirUsuarioSemCarrinhoComSucesso() {

        String idUsuario = "PVQzzj9BAIGm2p3p";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .statusCode(200)
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
        ;
    }
}
