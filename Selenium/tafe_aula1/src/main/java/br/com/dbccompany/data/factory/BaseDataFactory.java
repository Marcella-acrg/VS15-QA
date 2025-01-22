package br.com.dbccompany.data.factory;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class BaseDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    public static String vazio = StringUtils.EMPTY;




}
