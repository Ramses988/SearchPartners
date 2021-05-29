package com.search_partners.web.chat;

import com.search_partners.model.ChatMessage;
import com.search_partners.util.SecurityUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/messages")
    public String getChat() {
        return "chat/chat";
    }

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        System.out.println("Reuest: " + SecurityUtil.authUserId());
        return message;
    }

}
