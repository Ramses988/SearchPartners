package com.search_partners.model;

import com.search_partners.model.abstractentity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "send_mails")
@NoArgsConstructor
@AllArgsConstructor
public class SendMail extends AbstractBaseEntity {

    private String subject;
    private String mail;
    private LocalDateTime date;
    private int status;

}