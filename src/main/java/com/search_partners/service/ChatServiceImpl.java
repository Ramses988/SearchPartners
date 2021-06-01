package com.search_partners.service;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.ChatRoom;
import com.search_partners.model.User;
import com.search_partners.repository.ChatMessageRepository;
import com.search_partners.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {

    private final UserService userService;
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository roomRepository;

    @Autowired
    public ChatServiceImpl(UserService userService, ChatMessageRepository messageRepository, ChatRoomRepository roomRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ChatMessage sendMessage(ChatMessage message, Long id) {
        message.setSenderId(userService.getUser(id));
        message.setRecipientId(userService.getUser(2));
        message.setChatId(getChatRoom(message));
        message.setDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    private ChatRoom getChatRoom(ChatMessage message) {
        String id = getIdChat(message);
        ChatRoom room = roomRepository.findByChatId(id).orElse(null);
        if (Objects.nonNull(room)) {
            return room;
        } else {
            ChatRoom newRoom = new ChatRoom(id, message.getSenderId(), message.getRecipientId());
            return roomRepository.save(newRoom);
        }
    }

    private String getIdChat(ChatMessage message) {
        if (message.getSenderId().getId() > message.getRecipientId().getId())
            return message.getRecipientId().getId() + "_" + message.getSenderId().getId();
        else
            return message.getSenderId().getId() + "_" + message.getRecipientId().getId();
    }

}