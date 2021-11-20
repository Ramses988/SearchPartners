package com.search_partners.web.sell;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.SellBusinessService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class SellBusinessController {

    private final SellBusinessService service;
    private final CountryAndCityService countryAndCityService;

    @GetMapping("/sell")
    public String getSellBusiness(@RequestParam(defaultValue = "0", required = false) int page,
                           Model model) {
        model.addAttribute("posts", service.getPosts(page));
        List<Country> countries = countryAndCityService.getAllCountries();
        countries.get(0).setName("Страна");
        model.addAttribute("countries", countries);
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Город", "any"));
        model.addAttribute("cities", cityList);
        model.addAttribute("local", "");
        model.addAttribute("category", 2);
        return "post/posts";
    }

    @GetMapping("/sell/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", service.getPostWithComments(id));
        return "post/post";
    }

}