package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class DeletarUsuariosTest {

    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaExcluirUsuarioSemCarrinho() {

        usuarioClient.deletarUsuarios(UsuarioDataFactory.getUltimoIdLista())
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/excluir_usuario_sem_carrinho.json"))
        ;
    }


    @Test
    public void testExcluirUsuarioSemCarrinhoComSucesso() {

        usuarioClient.deletarUsuarios(UsuarioDataFactory.getUltimoIdLista())
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"))
        ;
    }

    @Test
    public void testExcluirUsuarioComIDInvalido() {

        usuarioClient.deletarUsuarios(UsuarioDataFactory.getIdInvalido())
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }

    @Test
    public void testExcluirUsuarioComCampoDeIDVazio() {
        usuarioClient.deletarUsuarios(" ")
        .then()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Nenhum registro excluído"))
        ;
    }
}



