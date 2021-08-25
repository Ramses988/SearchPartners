package com.search_partners.service;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.ChatRoom;
import com.search_partners.model.User;
import com.search_partners.repository.ChatMessageRepository;
import com.search_partners.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<User> getUsers(Long id) {
        List<ChatRoom> rooms = roomRepository.findAllOwnerRooms(id);
        List<User> users = new ArrayList<>();
        for (ChatRoom room : rooms) {
            users.add(checkUser(room, id));
        }
        return users;
    }

    private User checkUser(ChatRoom room, Long id) {
        User user;
        if (id != room.getSenderId().getId())
            user = room.getSenderId();
        else
            user = room.getRecipientId();

        if (Objects.nonNull(room.getLastMessage()) && !room.getLastMessage().getContent().isEmpty()) {
            String content = room.getLastMessage().getContent();
            if (content.length() < 29)
                user.setContent(content);
            else
                user.setContent(content.substring(0, 24) + "...");
        }

        return user;
    }

    @Override
    public ChatMessage sendMessage(ChatMessage message, Long id) {
        message.setSenderId(userService.getUser(id));
        message.setRecipientId(userService.getUser(message.getRecipientId().getId()));
        ChatRoom room = getChatRoom(message);
        message.setChatId(room);
        message.setDate(LocalDateTime.now());
        ChatMessage response = messageRepository.save(message);
        room.setLastMessage(response);
        roomRepository.save(room);
        return response;
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