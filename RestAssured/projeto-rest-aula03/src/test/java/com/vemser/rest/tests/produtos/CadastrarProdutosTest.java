package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import org.junit.jupiter.api.Test;

public class CadastrarProdutosTest {

    private ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testCadastrarProdutosComDadosValidos(){

        ProdutoRequest produto = ProdutoDataFactory.produtoValido();

    }
}
