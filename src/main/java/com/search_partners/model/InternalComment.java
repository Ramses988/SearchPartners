package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.model.abstractentity.AbstractBaseEntity;
import com.search_partners.model.abstractentity.AbstractInternalComment;
import com.search_partners.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "internal_comments")
public class InternalComment extends AbstractInternalComment {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public InternalComment() {
        super();
    }

    public InternalComment(String text, LocalDateTime date, Comment comment, User user) {
        super(text, date, user);
        this.comment = comment;
    }

}