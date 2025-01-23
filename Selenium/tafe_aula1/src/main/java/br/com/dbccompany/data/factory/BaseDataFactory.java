package br.com.dbccompany.data.factory;

import br.com.dbccompany.model.LoginModel;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import utils.Manipulation;

import java.util.Locale;
import java.util.Properties;

public class BaseDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    public static String vazio = StringUtils.EMPTY;
    public static final Properties prop = Manipulation.getProp();

    public static LoginModel loginValido() {
        LoginModel login = new LoginModel();
        login.setEmail("usuario_teste@gmail.com");
        login.setPassword("teste123");

        return login;
    }

    public static String emailAleatorio() {
        return faker.internet().emailAddress();
    }

    public static String nomeValido() {
        return faker.name().fullName();
    }

    public static String senhaAleatoria() {
        return faker.internet().password();
    }
}
