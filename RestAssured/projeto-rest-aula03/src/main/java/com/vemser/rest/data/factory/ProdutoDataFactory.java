package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.ProdutoRequest;
import io.restassured.response.Response;
import net.datafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class ProdutoDataFactory {

    static Faker faker = new Faker(new Locale("PT-BR"));
    static Random geradorBolean = new Random();

    static ProdutoClient produtoClient = new ProdutoClient();

    private static ProdutoRequest novoProduto() {
        ProdutoRequest produto = new ProdutoRequest();
        produto.setNome(faker.commerce().productName());
        produto.setPreco(faker.number().numberBetween(1,100));
        produto.setDescricao(faker.lorem().sentence());
        produto.setQuantidade(faker.number().numberBetween(1,100));

        return produto;
    }

    public static ProdutoRequest produtoValido(){
        return novoProduto();
    }

    public static ProdutoRequest produtoSemNome() {
        ProdutoRequest produto = novoProduto();
        produto.setNome("");

        return produto;
    }

    public static ProdutoRequest produtoSemDescricao() {
        ProdutoRequest produto = novoProduto();
        produto.setDescricao("");

        return produto;
    }

    public static String getIdInvalido() {
        String id = faker.idNumber().invalid();
        return id;
    }

    public static String getNomeProdutoInvalido(){
        String nome = faker.lorem().characters(0, 256);
        return nome;
    }

    public static Object[] cadastrarProdutoRetornarID(){
        ProdutoRequest produtoRequest = novoProduto();
        String idExistente = produtoClient.cadastrarProdutos(produtoRequest, LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido())).path("_id");

        return new Object[]{produtoRequest, idExistente};
    }

    public static Object[] atualizarProdutoRetornarID(){
        ProdutoRequest produtoRequest = novoProduto();
        System.out.println(novoProduto());
        String idExistente = produtoClient.cadastrarProdutos(produtoRequest, LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido())).path("_id");
        produtoRequest.setNome(faker.commerce().productName());
        produtoRequest.setDescricao(faker.lorem().sentence());

        return new Object[]{produtoRequest, idExistente};
    }

    public static Object[] atualizarProdutoComNomeVazio(){
        ProdutoRequest produtoRequest = novoProduto();
        System.out.println(novoProduto());
        String idExistente = produtoClient.cadastrarProdutos(produtoRequest, LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido())).path("_id");
        produtoRequest.setNome("");

        return new Object[]{produtoRequest, idExistente};
    }

    public static Object[] atualizarProdutoComPrecoZerado(){
        ProdutoRequest produtoRequest = novoProduto();
        System.out.println(novoProduto());
        String idExistente = produtoClient.cadastrarProdutos(produtoRequest, LoginDataFactory.obterTokenDeAtutenticacao(LoginDataFactory.novoLoginValido())).path("_id");
        produtoRequest.setPreco(0);

        return new Object[]{produtoRequest, idExistente};
    }

    public static String getIdVazio() {
        String id = " ";
        return id;
    }

    public static String getUltimoIdLista() {
        Response response =
                produtoClient.listarProdutos()
                        .then()
                        .extract().response()
                ;
        Integer quantidade = response.path("quantidade");
        String id = response.path("produtos[" + (quantidade - 1) + "]._id");

        return id;
    }
}
