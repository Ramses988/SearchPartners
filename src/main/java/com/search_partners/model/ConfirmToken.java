package com.search_partners.model;

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

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmToken(User user) {
        this.user = user;
        date = LocalDateTime.now();
        confirmToken = UUID.randomUUID().toString();
    }
}
