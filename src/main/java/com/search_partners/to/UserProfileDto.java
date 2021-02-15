package com.search_partners.to;

import lombok.Data;

@Data
public class UserProfileDto {

    private String realName;
    private String gender;
    private int busyness;
    private long country;
    private long city;
    private int day;
    private int month;
    private int year;

}
