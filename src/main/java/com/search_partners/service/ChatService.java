package com.search_partners.service;

import com.search_partners.model.ChatMessage;

public interface ChatService {

    ChatMessage sendMessage(ChatMessage message, Long id);

}
