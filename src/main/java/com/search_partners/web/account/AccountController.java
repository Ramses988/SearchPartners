package com.search_partners.web.account;

import com.search_partners.service.CountryAndCityService;
import com.search_partners.service.UserService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    private final UserService userService;
    private final CountryAndCityService countryService;

    @Autowired
    public AccountController(UserService userService, CountryAndCityService countryService) {
        this.userService = userService;
        this.countryService = countryService;
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(defaultValue = "false", required = false) boolean error,
                           Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("user", userService.getUser(SecurityUtil.authUserId()));
        model.addAttribute("countries", countryService.getAllCountries());
        return "account/profile";
    }

}