package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.ProdutoRequest;
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
        produto.setPreco(Double.parseDouble(faker.commerce().price().replace(",", ".")));
        produto.setDescricao(faker.lorem().sentence());
        produto.setQuantidade(faker.number().numberBetween(1,100));

        return produto;
    }

    public static ProdutoRequest produtoValido(){
        return novoProduto();
    }
}
