package com.search_partners.web.account;

import com.search_partners.service.UserService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    private final UserService service;

    @Autowired
    public AccountController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false", required = false) boolean error,
                           Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("user", service.getUser(SecurityUtil.authUserId()));
        return "account/profile";
    }

}