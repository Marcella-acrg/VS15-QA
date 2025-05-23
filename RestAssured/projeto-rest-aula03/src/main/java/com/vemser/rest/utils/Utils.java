package com.vemser.rest.utils;

import com.vemser.rest.data.factory.LoginDataFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static Properties loadProperties(){
        try(InputStream is = LoginDataFactory.class.getClassLoader().getResourceAsStream("login.properties")) {
            if( is == null) {
                throw new FileNotFoundException("Arquivo de propriedades não encontrado");

            }
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
