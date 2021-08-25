package com.search_partners.web.chat;

import com.search_partners.model.ChatMessage;
import com.search_partners.service.ChatService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    private final ChatService service;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/chat")
    public String getChat(Model model) {
        model.addAttribute("users", service.getUsers(SecurityUtil.authUserId()));
        return "chat/chat";
    }

    @MessageMapping("/messages")
    public void sendMessage(@Payload ChatMessage message) {
        ChatMessage response = service.sendMessage(message, SecurityUtil.authUserId());
        messagingTemplate.convertAndSend("/chat/" + response.getRecipientId().getEmail() + "/"
                + response.getRecipientId().getId(), response);
    }

}
