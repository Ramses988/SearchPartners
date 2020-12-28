package com.search_partners.web.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String getPosts() {
        return "post/posts";
    }

}
