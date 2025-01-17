package com.vemser.rest.tests.usuarios;

import com.vemser.rest.model.UsuarioRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testCadastrarUsuarioComSucesso(){

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Maria Clara Coeho");
        usuario.setEmail("mariaclara@qa.com.br");
        usuario.setPassword("teste");
        usuario.setAdministrador("true");

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .statusCode(201)
                .assertThat()
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
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
                .log().all()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .log().all()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"))
        ;
    }

    @Test
    public void testCadastrarUsuarioComEmailExistente(){

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome("Marcella Araújo");
        usuario.setEmail("marcella@qa.com.br ");
        usuario.setPassword("qa");
        usuario.setAdministrador("true");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/usuarios")
        .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Este email já está sendo usado"))
        ;
    }
}
