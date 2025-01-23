package br.com.dbccompany.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String password;
    private String birthDay;
    private String birthMonth;
    private String birthYear;
    private String company;
    private String address;
    private String address2;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobileNumber;

    public String getFullName(){
        return firstname + "" + lastname;
    }
}
