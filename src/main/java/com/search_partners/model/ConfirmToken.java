package com.search_partners.model;

import com.search_partners.model.abstractentity.AbstractBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirm_tokens")
public class ConfirmToken extends AbstractBaseEntity {

    private LocalDateTime date;

    @Column(name = "confirm_token")
    private String confirmToken;

    private int type;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmToken(User user, int type) {
        this.user = user;
        this.type = type;
        date = LocalDateTime.now();
        confirmToken = UUID.randomUUID().toString();
    }
}
