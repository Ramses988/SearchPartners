package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContactDto {

    @NotBlank(message = "Имя не должно быть пустым!")
    @Size(max = 30, message = "Максимальный размер поля имя, 30 символов!")
    private String name;

    @NotBlank(message = "Email не должен быть пустым!")
    @Email(message = "Email должен иметь формат адреса электронной почты!")
    private String email;

    @NotBlank(message = "Сообщение не должно быть пустое!")
    private String message;

}