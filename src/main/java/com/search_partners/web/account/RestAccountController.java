package com.search_partners.web.account;

import com.search_partners.service.UserService;
import com.search_partners.to.UserProfileDto;
import com.search_partners.util.SecurityUtil;
import com.search_partners.web.city.RestCityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RestAccountController.REST_URL)
public class RestAccountController {

    private UserService service;

    @Autowired
    public RestAccountController(UserService service) {
        this.service = service;
    }

    static final String REST_URL = "/rest/account";

    @PostMapping("/change-user-profile")
    public void changeUserProfile(UserProfileDto user) {
        service.saveUserProfile(user, SecurityUtil.authUserId());
    }

}