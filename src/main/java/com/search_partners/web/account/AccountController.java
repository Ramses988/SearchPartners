package com.search_partners.web.account;

import com.search_partners.model.City;
import com.search_partners.model.User;
import com.search_partners.service.CountryAndCityService;
import com.search_partners.service.UserService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "account/register";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        User user = userService.getUserWithCity(SecurityUtil.authUserId());
        model.addAttribute("user", user);
        model.addAttribute("countries", countryService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка"));
        if (user.getCountry().getId() > 0)
            cityList.addAll(countryService.getCities(user.getCountry().getId()));
        model.addAttribute("cities", cityList);
        return "account/profile";
    }

}