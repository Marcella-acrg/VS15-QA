package com.vemser.rest.tests.usuarios;

import com.vemser.rest.model.UsuarioRequest;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class UsuariosTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }


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
                .pathParam("nome", nomeUsuarioInexistente)
        .when()
                .get("/usuarios/{nome}")
        .then()
                .statusCode(400)
        ;
    }

    @Test
    public void testBuscarPeloEmailUsuarioInexistente() {

        String emailUsuarioInexistente = "gilma@qa.com.br";

        given()
                .pathParam("email", emailUsuarioInexistente)
        .when()
                .get("/usuarios/{email}")
        .then()
                .statusCode(400)
        ;
    }


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

    //Hamcrest
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
                .header("Content-Type", "application/json; charset=utf-8")
                .time(lessThan(5000L))
                .statusCode(200)
                .body("_id", equalTo(idUsuario))
                .body("email", containsStringIgnoringCase("marcella"))

        ;
    }

    //Assertions realiza o teste de apenas um erro, ou seja, para no primeiro e não identifica os demais
    @Test
    public void testBuscarUsuarioPorIDValidoComSucessoAssertions() {

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

        Assertions.assertEquals("Marcella Araújo", response.getNome());
        Assertions.assertEquals("m@rcella@qa.com.br", response.getEmail());
        Assertions.assertEquals("teste", response.getPassword());
        Assertions.assertEquals("trueeee", response.getAdministrador());
        Assertions.assertNotNull(response.getId());
    }

    //SoftAssertions ou Assertions All identifica todos os erros de uma única vez
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
                () -> Assertions.assertEquals("Marcellaaa Araújo", response.getNome()),
                () -> Assertions.assertEquals("m@rcella@qa.com.br", response.getEmail()),
                () -> Assertions.assertEquals("testeeeee", response.getPassword()),
                () -> Assertions.assertEquals("trueeee", response.getAdministrador()),
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
                .statusCode(400)
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
        ;
    }


    @Test
    public void testSchemaListarUsuarioPorNome() {

        String nome = "Marcella Araújo";

        given()
                .queryParam("nome", nome)
        .when()
                .get("/usuarios")
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_nome.json"))
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
    public void testCadastrarUsuarioComSucesso(){

        UsuarioRequest usuario = new UsuarioRequest();
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

        UsuarioRequest usuario = new UsuarioRequest();
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

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Marcella Coelho");
        usuario.setEmail("marcella@qa.com.br ");
        usuario.setPassword("qa123");
        usuario.setAdministrador("false");

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

        UsuarioRequest usuarioAtualizado = new UsuarioRequest();
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
    public void testSchemaExcluirUsuarioSemCarrinho() {

        String idUsuario = "PVQzzj9BAIGm2p3p";

        given()
                .pathParam("_id", idUsuario)
        .when()
                .delete("/usuarios/{_id}")
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/excluir_usuario_sem_carrinho.json"))
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
