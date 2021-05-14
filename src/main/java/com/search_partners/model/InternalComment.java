package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "internal_comments")
public class InternalComment extends AbstractBaseEntity implements Comparable<InternalComment>  {

    private String text;
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @JsonSerialize(converter = UserUtil.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public int compareTo(InternalComment o) {
        return date.compareTo(o.date);
    }
}