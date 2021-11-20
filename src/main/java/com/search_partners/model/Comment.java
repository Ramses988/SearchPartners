package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.model.abstractentity.AbstractBaseEntity;
import com.search_partners.model.abstractentity.AbstractComment;
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
public class Comment extends AbstractComment implements Comparable<Comment> {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<InternalComment> internalComments;

    public Comment() {
        super();
    }

    public Comment(String text, LocalDateTime date, Post post, User user) {
        super(text, date, user);
        this.post = post;
    }

}