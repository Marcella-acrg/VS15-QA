package com.vemser.rest.client;

import com.vemser.rest.model.ProdutoRequest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient {

    private final String PRODUTOS = "/produtos";

    public Response cadastrarProdutos(ProdutoRequest produto, String authorization) {

        return
                given()
                        .spec(super.set())
                        .header("Authorization", authorization)
                        .body(produto)
                .when()
                        .post(PRODUTOS)
                ;
    }

    public Response listarProdutos() {

        return
                given()
                        .spec(super.set())
                .when()
                        .get(PRODUTOS)
                ;
    }

    public Response listarProdutosPorNome(String nome) {

        return
                given()
                        .spec(super.set())
                        .pathParam("nome", nome)
                .when()
                        .get(PRODUTOS + "/{nome}")
                ;
    }

    public Response listarProdutosPorId(String id) {

        return
                given()
                        .spec(super.set())
                        .pathParam("_id", id)
                .when()
                        .get(PRODUTOS + "/{_id}")
                ;
    }

    public Response deletarProdutos(String id, String authorization) {

        return
                given()
                        .spec(super.set())
                        .header("Authorization", authorization)
                        .pathParam("_id", id)
                .when()
                        .delete(PRODUTOS + "/{_id}")
                ;
    }


    public Response atualizarProdutosPorId(ProdutoRequest produto, String id, String authorization) {

        return
                given()
                        .spec(super.set())
                        .header("Authorization", authorization)
                        .pathParam("_id", id)
                        .body(produto)
                .when()
                        .put(PRODUTOS + "/{_id}")
                ;
    }
}
