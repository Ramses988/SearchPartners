package com.search_partners.model.abstractentity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.search_partners.model.InternalComment;
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
public abstract class AbstractInternalComment extends AbstractBaseEntity implements Comparable<AbstractInternalComment> {

    private String text;
    private LocalDateTime date;

    @JsonSerialize(converter = UserUtil.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public int compareTo(AbstractInternalComment o) {
        return date.compareTo(o.getDate());
    }

}
