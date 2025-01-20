package com.vemser.rest.client;

import com.vemser.rest.model.ProdutoRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient {

    private final String PRODUTOS = "/produtos";

    public Response cadastrarProdutos(ProdutoRequest produto) {

        return
                given()
                        .spec(super.set())
                        .body(produto)
                .when()
                        .post(PRODUTOS)
                ;
    }

}
