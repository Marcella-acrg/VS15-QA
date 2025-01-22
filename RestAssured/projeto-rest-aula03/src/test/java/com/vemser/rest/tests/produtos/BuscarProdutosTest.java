package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BuscarProdutosTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaBuscarTodosProdutosCadastrados() {

        Response response = produtoClient.listarProdutos()
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/todos_produtos_cadastrados.json"))
                .extract().response()
        ;
    }


    @Test
    public void testBuscarTodosProdutosCadastradosComSucesso() {

        Response response = produtoClient.listarProdutos()
        .then()
                .statusCode(200)
                .extract().response()
        ;

        List<ProdutoResponse> produtos = response.jsonPath().getList("produtos", ProdutoResponse.class);
        int qtd = response.path("quantidade");

        Assertions.assertAll("response",
                () -> Assertions.assertNotNull(produtos.get(0).getNome()),
                () -> Assertions.assertNotNull(produtos.get(0).getPreco()),
                () -> Assertions.assertNotNull(produtos.get(0).getDescricao()),
                () -> Assertions.assertNotNull(produtos.get(0).getQuantidade()),
                () -> Assertions.assertNotNull(produtos.get(0).getId())
        );
    }

    @Test
    public void testBuscarPeloNomeProdutoInexistente() {

        String produtoInvalido = ProdutoDataFactory.getNomeProdutoInvalido();
        produtoClient.listarProdutosPorNome(produtoInvalido)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;
    }

    @Test
    public void testBuscarPeloIdProdutoInexistente() {

        String idInvalido = ProdutoDataFactory.getIdInvalido();
        produtoClient.listarProdutosPorId(idInvalido)
        .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Produto não encontrado"))
        ;
    }
}
