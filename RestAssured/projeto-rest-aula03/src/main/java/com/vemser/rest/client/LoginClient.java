package com.vemser.rest.client;

import com.vemser.rest.model.LoginRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class LoginClient extends BaseClient {

    private final String LOGIN = "/login";

    public Response logarUsuarios(LoginRequest login) {

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGIN)
                ;
    }
}
