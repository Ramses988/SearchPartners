package com.search_partners.model.abstractentity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.model.Comment;
import com.search_partners.model.User;
import com.search_partners.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractComment extends AbstractBaseEntity implements Comparable<Comment>  {

    private String text;
    private LocalDateTime date;

    @JsonSerialize(converter = UserUtil.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public int compareTo(Comment c) {
        return c.getDate().compareTo(date);
    }

}
