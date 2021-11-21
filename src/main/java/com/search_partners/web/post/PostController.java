package com.search_partners.web.post;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.model.Post;
import com.search_partners.model.User;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.PostService;
import com.search_partners.service.interfaces.UserService;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CountryAndCityService countryAndCityService;

    @Autowired
    public PostController(PostService postService, UserService userService,
                          CountryAndCityService countryAndCityService) {
        this.postService = postService;
        this.userService = userService;
        this.countryAndCityService = countryAndCityService;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam(defaultValue = "0", required = false) int page,
                           Model model) {
        model.addAttribute("posts", postService.getPosts(page));
        List<Country> countries = countryAndCityService.getAllCountries();
        countries.get(0).setName("Страна");
        model.addAttribute("countries", countries);
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Город", "any"));
        model.addAttribute("cities", cityList);
        model.addAttribute("local", "");
        model.addAttribute("category", 1);
        return "post/posts";
    }

    @GetMapping("/posts/{country}/{city}")
    public String getPostsWithFilters(@PathVariable("country") String country, @PathVariable("city") String city,
                                      @RequestParam(defaultValue = "0", required = false) int page, Model model) {
        model.addAttribute("posts", postService.getPostsWithFilters(country, city, page));
        List<Country> countries = countryAndCityService.getAllCountries();
        countries.get(0).setName("Страна");
        model.addAttribute("countries", countries);
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Город", "any"));
        cityList.addAll(countryAndCityService.getCitiesFromName(country));
        model.addAttribute("cities", cityList);
        model.addAttribute("countryName", country);
        model.addAttribute("cityName", city);
        model.addAttribute("local", countryAndCityService.getNameWhereSearch(country, city));
        model.addAttribute("category", 1);
        return "post/posts";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", postService.getPostWithComments(id));
        model.addAttribute("category", 1);
        return "post/post";
    }

    @GetMapping("/manage/post/add")
    public String getAddPost(Model model) {
        User user = userService.getUserWithCity(SecurityUtil.authUserId());
        model.addAttribute("countryId", user.getCountry().getId());
        model.addAttribute("cityId", user.getCity().getId());
        model.addAttribute("post", new Post());
        model.addAttribute("countries", countryAndCityService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка", "any"));
        if (user.getCountry().getId() > 0)
            cityList.addAll(countryAndCityService.getCities(user.getCountry().getId()));
        model.addAttribute("cities", cityList);
        return "post/add";
    }

    @GetMapping("/manage/post/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostWithOwner(id, SecurityUtil.authUserId());
        model.addAttribute("countryId", post.getCountry().getId());
        model.addAttribute("cityId", post.getCity().getId());
        model.addAttribute("post", post);
        model.addAttribute("countries", countryAndCityService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка", "any"));
        if (post.getCountry().getId() > 0)
            cityList.addAll(countryAndCityService.getCities(post.getCountry().getId()));
        model.addAttribute("cities", cityList);
        return "post/add";
    }

    @GetMapping("/manage/post/list")
    public String getListManagePosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts(SecurityUtil.authUserId()));
        return "post/list";
    }

}