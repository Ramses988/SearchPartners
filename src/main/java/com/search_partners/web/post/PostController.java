package com.search_partners.web.post;

import com.search_partners.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam(defaultValue = "0", required = false) int page,
                           Model model) {
        model.addAttribute("posts", service.getPosts(page));
        return "post/posts";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", service.getPost(id));
        return "post/post";
    }

}