package br.com.dbccompany.data.factory;

import br.com.dbccompany.model.LoginModel;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class BaseDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    public static String vazio = StringUtils.EMPTY;

    public static String emailValidoLogin() {
        LoginModel login = novoLogin();
        login.setEmail("usuario_teste@gmail.com");
        return login.getEmail();
    }

    public static String emailValido() {
        LoginModel login = novoLogin();
        return login.getEmail();
    }

    public static String senhaValida() {
        LoginModel login = novoLogin();
        return login.getPassword();
    }

    public static String emailAleatorio() {
        return faker.internet().emailAddress();
    }

    public static String nomeValido() {
        return faker.name().fullName();
    }

    public static LoginModel novoLogin() {
        LoginModel login = new LoginModel();
        login.setEmail("usuario_teste2@gmail.com");
        login.setPassword("teste123");

        return login;
    }


}
