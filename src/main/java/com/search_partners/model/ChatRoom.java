package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="chat_room")
public class ChatRoom extends AbstractBaseEntity {

    @Column(name="chat_id")
    private String chatId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatId")
    private List<ChatMessage> chatMessages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipientId;

}