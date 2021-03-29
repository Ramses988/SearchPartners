package com.search_partners.to;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    @NotBlank(message = "Заголовок не должен быть пустым!")
    @Size(max = 100, message = "Максимальный размер поля Заголовок, 100 символов!")
    @Pattern(regexp = "[^<>]{1,100}", message = "Запрещенные символы в поле заголовок!")
    private String title;

    @NotBlank(message = "Текст записи не должен быть пустым!")
    @Size(max = 3000)
    private String text;

    @Min(0)
    private int country;

    @Min(0)
    private int city;

}
