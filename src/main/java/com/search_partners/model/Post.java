package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> commentList;

    @JsonIgnore
    private int active = 1;

    @Transient
    private String duration;
    @Transient
    private String durationShort;

}