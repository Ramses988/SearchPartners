package com.search_partners.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="posts")
public class Post extends AbstractBaseEntity {

    private String title;
    private String text;
    private LocalDateTime date;
    private long show;
    private long comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient
    private String duration;
    @Transient
    private String durationShort;
}