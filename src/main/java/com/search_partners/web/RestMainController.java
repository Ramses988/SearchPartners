package com.search_partners.web;

import com.search_partners.service.interfaces.UserService;
import com.search_partners.to.ContactDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RestMainController {

    private final UserService userService;

    @PostMapping("/rest/contact/send")
    public void sendMessageFromContact(@Valid ContactDto contact) {
        userService.sendMessageFromContact(contact);
    }

}