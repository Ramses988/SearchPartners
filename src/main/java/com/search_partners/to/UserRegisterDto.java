package com.search_partners.to;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String gender;
    private int country;
    private int city;

}