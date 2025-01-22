package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import com.vemser.rest.model.ProdutoResponse;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class AtualizarProdutosTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaAtualizarNomeDescricaoDoProduto() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        Object[] produtoValido = ProdutoDataFactory.atualizarProdutoRetornarID();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        System.out.println("PRODUTO ATUALIZADO " + produto);
        String id = (String)produtoValido[1];
        ProdutoResponse response = produtoClient.atualizarProdutosPorId(produto, id, authorization)
        .then()
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/atualizar_produto.json"))
                .extract().as(ProdutoResponse.class)
        ;
    }


    @Test
    public void testAtualizarNomeDescricaoDoProdutoComSucesso() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        Object[] produtoValido = ProdutoDataFactory.atualizarProdutoRetornarID();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        System.out.println("PRODUTO ATUALIZADO " + produto);
        String id = (String)produtoValido[1];
        ProdutoResponse response = produtoClient.atualizarProdutosPorId(produto, id, authorization)
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"))
                .extract().as(ProdutoResponse.class)
        ;
    }

    @Test
    public void testAtualizarProdutoComCampoNomeVazio() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        Object[] produtoValido = ProdutoDataFactory.atualizarProdutoComNomeVazio();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        System.out.println("PRODUTO ATUALIZADO " + produto);
        String id = (String)produtoValido[1];
        ProdutoResponse response = produtoClient.atualizarProdutosPorId(produto, id, authorization)
        .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"))
                .extract().as(ProdutoResponse.class)
        ;
    }

    @Test
    public void testAtualizarProdutoComCampoPrecoZero() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        Object[] produtoValido = ProdutoDataFactory.atualizarProdutoComPrecoZerado();
        ProdutoRequest produto = (ProdutoRequest)produtoValido[0];
        System.out.println("PRODUTO ATUALIZADO " + produto);
        String id = (String)produtoValido[1];
        ProdutoResponse response = produtoClient.atualizarProdutosPorId(produto, id, authorization)
        .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .body("preco", equalTo("preco deve ser um número positivo"))
                .extract().as(ProdutoResponse.class)
        ;
    }
}
