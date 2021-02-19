package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordDto {

    @NotBlank(message = "Пароль не должен быть пустым!")
    @Size(min = 7, max = 30, message = "Пароль должен находиться в диапазоне от 7 до 30!")
    private String currentPassword;

    @NotBlank(message = "Пароль не должен быть пустым!")
    @Size(min = 7, max = 30, message = "Пароль должен находиться в диапазоне от 7 до 30!")
    private String newPassword;

    @NotBlank(message = "Пароль не должен быть пустым!")
    @Size(min = 7, max = 30, message = "Пароль должен находиться в диапазоне от 7 до 30!")
    private String confirmPassword;

}