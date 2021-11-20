package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SellPostDto extends PostDto {

    @Min(0)
    private long price;

    @NotBlank(message = "Поле (возраст бизнеса) не должно быть пустым!")
    @Size(max = 100, message = "Максимальный размер поля (возраст бизнеса), 100 символов!")
    @Pattern(regexp = "[^<>]{1,100}", message = "Запрещенные символы в поле (возраст бизнеса)!")
    private String age;

    @Min(0)
    private long profit;

    @Min(0)
    private long income;

}
