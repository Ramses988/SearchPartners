package com.search_partners.web.post;

import com.search_partners.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        model.addAttribute("posts", service.getPosts());
        return "post/posts";
    }

}