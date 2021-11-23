package com.search_partners.web.sell;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.model.Post;
import com.search_partners.model.SellBusiness;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.SellBusinessService;
import com.search_partners.util.SecurityUtil;
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
        model.addAttribute("local", "Продажа/покупка готового бизнеса");
        model.addAttribute("category", 2);
        return "post/posts";
    }

    @GetMapping("/sell/{country}/{city}")
    public String getPostsWithFilters(@PathVariable("country") String country, @PathVariable("city") String city,
                                      @RequestParam(defaultValue = "0", required = false) int page, Model model) {
        model.addAttribute("posts", service.getPostsWithFilters(country, city, page));
        List<Country> countries = countryAndCityService.getAllCountries();
        countries.get(0).setName("Страна");
        model.addAttribute("countries", countries);
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Город", "any"));
        cityList.addAll(countryAndCityService.getCitiesFromName(country));
        model.addAttribute("cities", cityList);
        model.addAttribute("countryName", country);
        model.addAttribute("cityName", city);
        model.addAttribute("local", "Продажа/покупка готового бизнеса " +countryAndCityService.getNameWhereSearch(country, city));
        model.addAttribute("category", 2);
        return "post/posts";
    }

    @GetMapping("/manage/sell/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        SellBusiness sellBusiness = service.getPostWithOwner(id, SecurityUtil.authUserId());
        model.addAttribute("countryId", sellBusiness.getCountry().getId());
        model.addAttribute("cityId", sellBusiness.getCity().getId());
        model.addAttribute("post", sellBusiness);
        model.addAttribute("countries", countryAndCityService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка", "any"));
        if (sellBusiness.getCountry().getId() > 0)
            cityList.addAll(countryAndCityService.getCities(sellBusiness.getCountry().getId()));
        model.addAttribute("cities", cityList);
        model.addAttribute("category", 2);
        return "post/add";
    }

    @GetMapping("/sell/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", service.getPostWithComments(id));
        model.addAttribute("category", 2);
        return "post/post";
    }

}