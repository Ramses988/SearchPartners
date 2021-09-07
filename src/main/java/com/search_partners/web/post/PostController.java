package com.search_partners.web.post;

import com.search_partners.model.City;
import com.search_partners.model.Post;
import com.search_partners.model.User;
import com.search_partners.service.CountryAndCityService;
import com.search_partners.service.PostService;
import com.search_partners.service.UserService;
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
        return "post/posts";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", postService.getPostWithComments(id));
        return "post/post";
    }

    @GetMapping("/manage/post/add")
    public String getAddPost(Model model) {
        User user = userService.getUserWithCity(SecurityUtil.authUserId());
        model.addAttribute("user", user);
        model.addAttribute("countries", countryAndCityService.getAllCountries());
        List<City> cityList = new ArrayList<>();
        cityList.add(new City(0, "Выберите из списка"));
        if (user.getCountry().getId() > 0)
            cityList.addAll(countryAndCityService.getCities(user.getCountry().getId()));
        model.addAttribute("cities", cityList);
        return "post/add";
    }

    @GetMapping("/manage/post/list")
    public String getListManagePosts() {
        return "post/list";
    }

}