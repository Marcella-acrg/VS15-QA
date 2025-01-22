package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.LoginRequest;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {

    private LoginClient loginClient = new LoginClient();

    @Test
    public void testSchemaLogarUsuario(){

        LoginRequest login = LoginDataFactory.novoLoginValido();

        loginClient.logarUsuarios(login)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/logar_usuario.json"))
        ;
    }

    @Test
    public void testLogarUsuario(){

        LoginRequest login = LoginDataFactory.novoLoginValido();

        loginClient.logarUsuarios(login)
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
        ;
    }

    @Test
    public void testLogarUsuarioComCampoEmailVazio(){

        LoginRequest login = LoginDataFactory.loginSemEmail();

        loginClient.logarUsuarios(login)
        .then()
                .assertThat()
                .statusCode(400)
                .body("email", equalTo("email não pode ficar em branco"))
        ;
    }

    @Test
    public void testLogarUsuarioComCampoPasswordVazio(){

        LoginRequest login = LoginDataFactory.loginSemPassword();

        loginClient.logarUsuarios(login)
        .then()
                .assertThat()
                .statusCode(400)
                .body("password", equalTo("password não pode ficar em branco"))
        ;
    }
}
