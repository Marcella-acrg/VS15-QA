package com.vemser.rest.tests.usuarios;

import com.vemser.rest.model.UsuarioResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetListarTest {

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost:3000";
    }

    @Test
    public void testBuscarTodosUsuariosCadastradosComSucesso() {

        Response response =
        given()
        .when()
                .get("/usuarios")
        .then()
                .statusCode(200)
                .extract().response()
        ;

        List<UsuarioResponse> usuarios = response.jsonPath().getList("usuarios", UsuarioResponse.class);

        Assertions.assertAll("response",
                () -> usuarios.forEach(nome ->Assertions.assertNotNull(nome)),
                () -> usuarios.forEach(email ->Assertions.assertNotNull(email)),
                () -> usuarios.forEach(password ->Assertions.assertNotNull(password)),
                () -> usuarios.forEach(administrador ->Assertions.assertNotNull(administrador)),
                () -> usuarios.forEach(id ->Assertions.assertNotNull(id))
        );
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
                .assertThat()
                .body("message", equalTo("Usuário não encontrado"))
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
                .assertThat()
                .body("message", equalTo("Usuário não encontrado"))
        ;
    }
}
