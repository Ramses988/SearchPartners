package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.util.UserUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="comments")
public class Comment extends AbstractBaseEntity implements Comparable<Comment> {

    private String text;
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @JsonSerialize(converter = UserUtil.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "comment")
    private List<InternalComment> internalComments;

    public Comment() {}

    public Comment(String text, LocalDateTime date, Post post, User user) {
        this.text = text;
        this.date = date;
        this.post = post;
        this.user = user;
    }

    @Override
    public int compareTo(Comment c) {
        return c.getDate().compareTo(date);
    }
}