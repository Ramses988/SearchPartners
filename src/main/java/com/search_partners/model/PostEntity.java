package com.search_partners.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostEntity {

    private long id;
    private String name;
    private String title;
    private LocalDateTime date;
    private String duration;
    private String durationShort;
    private String show;
    private String comments;
    private String initial;
    private String color;

}