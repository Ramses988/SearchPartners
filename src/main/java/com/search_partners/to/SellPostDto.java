package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SellPostDto extends PostDto {

    @NotBlank(message = "Поле (Стоимость продажи бизнеса) не должно быть пустым!")
    @Pattern(regexp = "^[0-9 ]+$", message = "В поле (Стоимость продажи бизнеса) могут быть только цифры!")
    private String price;

    @NotBlank(message = "Поле (возраст бизнеса) не должно быть пустым!")
    @Size(max = 100, message = "Максимальный размер поля (возраст бизнеса), 100 символов!")
    @Pattern(regexp = "[^<>]{1,100}", message = "Запрещенные символы в поле (возраст бизнеса)!")
    private String age;

    @NotBlank(message = "Поле (Чистая прибыль ежемесячно) не должно быть пустым!")
    @Pattern(regexp = "^[0-9 ]+$", message = "В поле (Чистая прибыль ежемесячно) могут быть только цифры!")
    private String profit;

    @NotBlank(message = "Поле (Оборот бизнеса ежемесячно) не должно быть пустым!")
    @Pattern(regexp = "^[0-9 ]+$", message = "В поле (Оборот бизнеса ежемесячно) могут быть только цифры!")
    private String income;

}
