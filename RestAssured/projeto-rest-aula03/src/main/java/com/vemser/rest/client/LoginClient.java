package com.vemser.rest.client;

import com.vemser.rest.model.LoginRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class LoginClient extends BaseClient {

    private final String LOGIN = "/login";

    public Response logarUsuarios(String email, String password) {

        return
                given()
                        .spec(super.set())
                        .body(new LoginRequest(email, password))
                .when()
                        .post(LOGIN)
                ;
    }
}
