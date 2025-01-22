package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import com.vemser.rest.model.ProdutoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;


public class BuscarProdutosPorIdTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaBuscarProdutoPorIDValido() {

        Object[] produtoValido = ProdutoDataFactory.cadastrarProdutoRetornarID();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        String id = (String)produtoValido[1];

        ProdutoResponse response = produtoClient.listarProdutosPorId(id)
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"))
                .extract().as(ProdutoResponse.class)
        ;
    }


    @Test
    public void testBuscarProdutoPorIDValidoComSucesso() {

        Object[] produtoValido = ProdutoDataFactory.cadastrarProdutoRetornarID();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        String id = (String)produtoValido[1];

        ProdutoResponse response = produtoClient.listarProdutosPorId(id)
        .then()
                .statusCode(200)
                .extract().as(ProdutoResponse.class)
        ;

        Assertions.assertAll("response",
                () -> Assertions.assertEquals(produto.getNome(), response.getNome()),
                () -> Assertions.assertEquals(produto.getPreco(), response.getPreco()),
                () -> Assertions.assertEquals(produto.getDescricao(), response.getDescricao()),
                () -> Assertions.assertEquals(produto.getQuantidade(), response.getQuantidade()),
                () -> Assertions.assertNotNull(response.getId())
        );
    }

    @Test
    public void testBuscarProdutoPorIDInexistente() {

        String idInvalido = ProdutoDataFactory.getIdInvalido();
        produtoClient.listarProdutosPorId(idInvalido)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;
    }

    @Test
    public void testBuscarProdutoPorIDComCampoVazio() {
        String getIdVazio = ProdutoDataFactory.getIdVazio();
        produtoClient.listarProdutosPorId(getIdVazio)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;
    }
}
