package br.com.dbccompany.data.factory;

import br.com.dbccompany.model.UsuarioModel;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UsuarioDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    public static String vazio = StringUtils.EMPTY;

    public static UsuarioModel novoUsuario () {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setFirstname(faker.name().firstName());
        usuario.setLastname(faker.name().lastName());

        String[] titles = {"#id_gender1", "#id_gender2"};
        String randomTitle = titles[faker.random().nextInt(titles.length)];
        usuario.setGender(randomTitle);

        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());

        LocalDate birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
        usuario.setBirthDay(String.valueOf(birthDate.getDayOfMonth()));
        usuario.setBirthMonth(birthDate.format(monthFormatter));
        usuario.setBirthYear(birthDate.format(yearFormatter));

        usuario.setCompany(faker.company().name());
        usuario.setAddress(faker.address().fullAddress());
        usuario.setAddress2(faker.address().secondaryAddress());

        String[] countries = {
                "India",
                "United States",
                "Canada",
                "Australia",
                "Israel",
                "New Zealand",
                "Singapore"
        };
        String randomCountry = countries[faker.random().nextInt(countries.length)];
        usuario.setCountry(randomCountry);

        usuario.setState(faker.address().state());
        usuario.setCity(faker.address().city());
        usuario.setZipcode(faker.address().zipCode());
        usuario.setMobileNumber(faker.phoneNumber().phoneNumber());

        return usuario;
    }

    public static UsuarioModel usuarioValido() {
        return novoUsuario();
    }
}
