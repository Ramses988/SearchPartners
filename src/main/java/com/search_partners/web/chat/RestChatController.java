package com.search_partners.web.chat;

import com.search_partners.model.ChatMessage;
import com.search_partners.service.interfaces.ChatService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestChatController.REST_URL)
public class RestChatController {

    static final String REST_URL = "/rest/chat";
    private final ChatService service;

    @Autowired
    public RestChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping("/history")
    public List<ChatMessage> getHistory(@RequestParam long recipientId) {
        return service.getHistory(recipientId, SecurityUtil.authUserId());
    }

    @PostMapping("/set-read")
    public void setRead(@RequestParam long recipientId) {
        service.setRead(recipientId, SecurityUtil.authUserId());
    }

    @PostMapping("/get-new-letter")
    public boolean getNewLetter() {
        return service.getNewLetter(SecurityUtil.authUserId());
    }

}