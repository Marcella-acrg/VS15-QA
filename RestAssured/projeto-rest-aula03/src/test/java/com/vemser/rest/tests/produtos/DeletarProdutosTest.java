package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class DeletarProdutosTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaExcluirProdutoSemCarrinho() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        produtoClient.deletarProdutos(ProdutoDataFactory.getUltimoIdLista(),authorization)
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/excluir_produto_sem_carrinho.json"))
        ;
    }


    @Test
    public void testExcluirProdutoSemCarrinhoComSucesso() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        produtoClient.deletarProdutos(ProdutoDataFactory.getUltimoIdLista(),authorization)
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testExcluirProdutoComIDInvalido() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        produtoClient.deletarProdutos(ProdutoDataFactory.getIdInvalido(), authorization)
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }

    @Test
    public void testExcluirProdutoComCampoDeIDVazio() {

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        produtoClient.deletarProdutos(" ", authorization)
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }
}
