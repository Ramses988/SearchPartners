package com.search_partners.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String title;
    private LocalDateTime date;
    private long show;
    private long comments;
    private String initial;
    private String color;

    @Transient
    private String duration;
    @Transient
    private String durationShort;

}