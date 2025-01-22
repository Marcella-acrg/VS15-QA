package com.vemser.rest.data.factory;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.model.LoginRequest;
import com.vemser.rest.utils.Utils;

import java.util.Properties;

public class LoginDataFactory {

    static LoginClient loginClient = new LoginClient();

    public static LoginRequest novoLoginValido() {
        return novoLoginUsuario();
    }

    private static LoginRequest novoLoginUsuario() {
        LoginRequest login = new LoginRequest();

        Properties props = Utils.loadProperties();
        String email = props.getProperty("email");
        String password = props.getProperty("password");

        login.setEmail(email);
        login.setPassword(password);

        return login;
    }

    public static LoginRequest loginSemEmail() {
        LoginRequest login = novoLoginUsuario();
        login.setEmail("");

        return login;
    }

    public static LoginRequest loginSemPassword() {
        LoginRequest login = novoLoginUsuario();
        login.setPassword("");

        return login;
    }

    public static String obterTokenDeAtutenticacao(LoginRequest login) {

        return loginClient.logarUsuarios(login)
        .then()
                .extract().path("authorization");
    }
}
