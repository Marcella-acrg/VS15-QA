package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.UsuarioRequest;
import io.restassured.response.Response;
import net.datafaker.Faker;
import java.util.Random;
import java.util.Locale;

public class UsuarioDataFactory {

    static Faker faker = new Faker(new Locale("PT-BR"));
    static Random geradorBolean = new Random();

    static UsuarioClient usuarioClient = new UsuarioClient();

    private static UsuarioRequest novoUsuario() {

        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNome(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(geradorBolean.nextBoolean()));

        return usuario;
    }

    public static UsuarioRequest usuarioValido() {
        return novoUsuario();
    }

    public static UsuarioRequest usuarioSemEmail() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setEmail("");

        return usuario;
    }

    public static UsuarioRequest usuarioComEmailExistente() {
        UsuarioRequest usuario = novoUsuario();
        usuario.setEmail(retornarEmailExistente());

        return usuario;
    }

    private static String retornarEmailExistente() {
        String emailExistente = usuarioClient.listarUsuarios().path("usuarios[0].email");

        return emailExistente;
    }

    public static Object[] cadastrarUsuarioRetornarID(){
        UsuarioRequest usuarioRequest = novoUsuario();
        String idExistente = usuarioClient.cadastrarUsuarios(usuarioRequest).path("_id");

        return new Object[]{usuarioRequest, idExistente};
    }

    public static Object[] atualizarUsuarioRetornarID(){
        UsuarioRequest usuarioRequest = novoUsuario();
        System.out.println(usuarioRequest);
        String idExistente = usuarioClient.cadastrarUsuarios(usuarioRequest).path("_id");
        usuarioRequest.setEmail(faker.internet().emailAddress());
        usuarioRequest.setPassword(faker.internet().password());

        return new Object[]{usuarioRequest, idExistente};
    }

    public static Object[] atualizarUsuarioComEmailVazio(){
        UsuarioRequest usuarioRequest = novoUsuario();
        System.out.println(usuarioRequest);
        String idExistente = usuarioClient.cadastrarUsuarios(usuarioRequest).path("_id");
        usuarioRequest.setEmail("");

        return new Object[]{usuarioRequest, idExistente};
    }

    public static Object[] atualizarUsuarioComPasswordVazio(){
        UsuarioRequest usuarioRequest = novoUsuario();
        System.out.println(usuarioRequest);
        String idExistente = usuarioClient.cadastrarUsuarios(usuarioRequest).path("_id");
        usuarioRequest.setPassword("");

        return new Object[]{usuarioRequest, idExistente};
    }

    public static String getIdInvalido() {
        String id = faker.idNumber().invalid();
        return id;
    }


    public static String getEmailInvalido() {
        String email = faker.internet().uuid();
        return email;
    }

    public static String getNomeInvalido(){
        String nome = faker.name().fullName();
        return nome;
    }

    public static String getIdVazio() {
        String id = "";
        return id;
    }

    public static String getUltimoIdLista() {
        Response response =
                usuarioClient.listarUsuarios()
                        .then()
                        .extract().response()
                        ;
                Integer quantidade = response.path("quantidade");
                String id = response.path("usuarios[" + (quantidade - 1) + "]._id");

                return id;
    }

    public static Object[] cadastrarUsuario(){
        UsuarioRequest usuarioRequest = novoUsuario();

        return new Object[]{usuarioRequest};
    }
}
