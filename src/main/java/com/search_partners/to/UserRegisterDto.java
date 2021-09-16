package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRegisterDto {

    //TODO: Check for special character
    @NotBlank(message = "Логин не должен быть пустым!")
    @Size(max = 15, message = "Максимальный размер поля логин, 15 символов!")
    private String name;

    //TODO: Check for special character
    @Email(message = "Email должен иметь формат адреса электронной почты!")
    @NotBlank(message = "Email не должен быть пустым!")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым!")
    @Size(min = 7, max = 30, message = "Пароль должен находиться в диапазоне от 7 до 30!")
    private String userPassword;

    @NotBlank(message = "Пароль не должен быть пустым!")
    @Size(min = 7, max = 30, message = "Пароль должен находиться в диапазоне от 7 до 30!")
    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "[UMF]")
    private String gender;

    @Min(0)
    private int country;

    @Min(0)
    private int city;

}