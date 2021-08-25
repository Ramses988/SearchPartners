package com.search_partners.repository;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @EntityGraph(attributePaths = {"senderId", "recipientId"}, type = EntityGraph.EntityGraphType.LOAD)
    List<ChatMessage> findAllByChatIdOrderByDateAsc(ChatRoom chatId);

}