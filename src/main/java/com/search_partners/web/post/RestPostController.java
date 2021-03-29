package com.search_partners.web.post;

import com.search_partners.service.PostService;
import com.search_partners.to.PostDto;
import com.search_partners.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RestPostController {

    private final PostService service;

    @Autowired
    public RestPostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/manage/rest/post/add-post")
    public void addPost(@Valid PostDto post) {
        service.savePost(post, SecurityUtil.authUserId());
    }

}