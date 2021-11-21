package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.search_partners.model.abstractentity.AbstractComment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="comments_sell")
public class CommentSell extends AbstractComment {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private SellBusiness post;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<InternalCommentSell> internalComments;

    public CommentSell() {
        super();
    }

    public CommentSell(String text, LocalDateTime date, SellBusiness post, User user) {
        super(text, date, user);
        this.post = post;
    }

}
