package com.search_partners.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="chat_room")
@NoArgsConstructor
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message")
    private ChatMessage lastMessage;

    @JsonIgnore
    @Column(name="user_read")
    private long userRead;

    public ChatRoom(String chatId, User senderId, User recipientId) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }
}