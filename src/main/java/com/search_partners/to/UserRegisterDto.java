package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRegisterDto {

    @NotBlank
    @Size(max = 15)
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 7, max = 30)
    private String password;

    @NotBlank
    @Size(min = 7, max = 30)
    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "[UMF]")
    private String gender;

    @Min(0)
    private int country;

    @Min(0)
    private int city;

}