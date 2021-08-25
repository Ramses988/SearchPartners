package com.search_partners.service;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.User;

import java.util.List;

public interface ChatService {

    ChatMessage sendMessage(ChatMessage message, Long id);

    List<User> getUsers(Long id);

}
