package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CadastrarProdutosTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaCadastrarProduto(){

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutos(produto, authorization)
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/cadastrar_produto.json"))
        ;
    }


    @Test
    public void testCadastrarProdutosComDadosValidos(){

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

        produtoClient.cadastrarProdutos(produto, authorization)
        .then()
                .statusCode(201)
                .assertThat()
                .body("message",equalTo("Cadastro realizado com sucesso"))
                .body("_id", notNullValue())
        ;
    }

    @Test
    public void testTentarCadastrarProdutoComNomeVazio(){

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        ProdutoRequest produtoSemNome = ProdutoDataFactory.produtoSemNome();

        produtoClient.cadastrarProdutos(produtoSemNome, authorization)
        .then()
                .assertThat()
                .statusCode(400)
                .body("nome", equalTo("nome não pode ficar em branco"))
        ;
    }

    @Test
    public void testTentarCadastrarProdutoComCampoDescricaoVazio(){

        String authorization = LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido());

        ProdutoRequest produtoSemDescricao = ProdutoDataFactory.produtoSemDescricao();

        produtoClient.cadastrarProdutos(produtoSemDescricao, authorization)
        .then()
                .assertThat()
                .statusCode(400)
                .body("descricao", equalTo("descricao não pode ficar em branco"))
        ;
    }
}
