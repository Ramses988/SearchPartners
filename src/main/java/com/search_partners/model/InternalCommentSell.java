package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.search_partners.model.abstractentity.AbstractInternalComment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "internal_comments_sell")
public class InternalCommentSell extends AbstractInternalComment {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentSell comment;

    public InternalCommentSell() {
        super();
    }

    public InternalCommentSell(String text, LocalDateTime date, CommentSell comment, User user) {
        super(text, date, user);
        this.comment = comment;
    }

}
