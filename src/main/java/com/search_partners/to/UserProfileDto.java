package com.search_partners.to;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserProfileDto {

    @Size(max = 30)
    private String realName;

    @NotBlank
    @Pattern(regexp = "[UMF]")
    private String gender;

    @Min(0)
    @Max(50)
    private int busyness;

    @Min(0)
    private long country;

    @Min(0)
    private long city;

    @Min(0)
    @Max(31)
    private int day;

    @Min(0)
    @Max(12)
    private int month;

    @Min(0)
    @Max(5000)
    private int year;

}