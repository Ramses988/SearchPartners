package com.search_partners.web.chat;

import com.search_partners.model.ChatMessage;
import com.search_partners.model.Provider;
import com.search_partners.model.User;
import com.search_partners.service.interfaces.ChatService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final ChatService service;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/chat/{selectId}")
    public String getChatName(@PathVariable(name="selectId") Long selectId, Model model) {
        if (SecurityUtil.authUserId() != selectId) {
            List<User> users = new ArrayList<>();
            Long activeId = service.getUserActive(selectId, SecurityUtil.authUserId(), users);
            users.addAll(service.getUsers(SecurityUtil.authUserId()));
            model.addAttribute("activeId", activeId);
            model.addAttribute("users", users);
            return "chat/chat";
        }
        return getChat(model);
    }

    @GetMapping("/chat")
    public String getChat(Model model) {
        model.addAttribute("activeId", 0);
        model.addAttribute("users", service.getUsers(SecurityUtil.authUserId()));
        return "chat/chat";
    }

    @MessageMapping("/messages")
    public void sendMessage(@Payload ChatMessage message) {
        ChatMessage response = service.sendMessage(message, SecurityUtil.authUserId());
        messagingTemplate.convertAndSend("/chat/" +
                ((Provider.LOCAL.getName().equals(response.getRecipientId().getProvider())) ? response.getRecipientId().getEmail() : response.getRecipientId().getUserId())
                + "/" + response.getRecipientId().getId(), response);
    }

}
