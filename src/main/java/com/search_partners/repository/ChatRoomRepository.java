package com.search_partners.repository;

import com.search_partners.model.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatId(String chatId);

    @EntityGraph(attributePaths = {"senderId", "recipientId", "lastMessage"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM ChatRoom r WHERE r.senderId.id=:id OR r.recipientId.id=:id")
    List<ChatRoom> findAllOwnerRooms(Long id);

    List<ChatRoom> findAllByUserRead(Long userRead);

}