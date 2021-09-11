package com.search_partners.service.interfaces;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.User;

import java.util.List;

public interface ChatService {

    ChatMessage sendMessage(ChatMessage message, Long id);

    List<ChatMessage> getHistory(Long recipientId, Long senderId);

    void setRead(Long recipientId, Long senderId);

    List<User> getUsers(Long id);

    boolean getNewLetter(Long id);

}
