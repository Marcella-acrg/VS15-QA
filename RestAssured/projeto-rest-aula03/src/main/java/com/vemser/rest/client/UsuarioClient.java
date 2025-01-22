package com.vemser.rest.client;

import com.vemser.rest.model.UsuarioRequest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class UsuarioClient extends BaseClient {

    private final String USUARIOS = "/usuarios";

    public Response cadastrarUsuarios(UsuarioRequest usuario) {

        return
                given()
                        .spec(super.set())
                        .body(usuario)
                .when()
                        .post(USUARIOS)
                ;
    }

    public Response listarUsuarios() {

        return
                given()
                        .spec(super.set())
                .when()
                        .get(USUARIOS)
                ;
    }

    public Response listarUsuariosPorNome(String nome) {

        return
                given()
                        .spec(super.set())
                        .pathParam("nome", nome)
                .when()
                        .get(USUARIOS + "/{nome}")
                ;
    }

    public Response listarUsuariosPorId(String id) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(USUARIOS + "/{_id}")
                ;
    }

    public Response deletarUsuarios(String id) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .delete(USUARIOS + "/{_id}")
                ;
    }

    public Response atualizarUsuariosPorId(UsuarioRequest usuario, String id) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                        .body(usuario)
                .when()
                        .put(USUARIOS + "/{_id}")
                ;
    }
}
