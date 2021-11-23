package com.search_partners.web.sell;

import com.search_partners.service.interfaces.SellBusinessService;
import com.search_partners.to.SellPostDto;
import com.search_partners.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RestSellBusinessController {

    private final SellBusinessService service;

    @PostMapping("/manage/rest/sell/add-post")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addPost(@Valid SellPostDto sellPost) {
        service.savePost(sellPost, SecurityUtil.authUserId());
    }

    @PostMapping("/manage/rest/sell/edit-post")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void editPost(@Valid SellPostDto post) {
        service.editPost(post, SecurityUtil.authUserId());
    }

    @PostMapping("/manage/rest/sell/set-close")
    public boolean closePost(Long id) {
        return service.closePost(id, SecurityUtil.authUserId());
    }

    @PostMapping("/manage/rest/sell/set-active")
    public boolean activePost(Long id) {
        return service.activePost(id, SecurityUtil.authUserId());
    }

    @PostMapping("/manage/rest/sell/delete-post")
    public boolean deletePost(Long id) {
        return service.deletePost(id, SecurityUtil.authUserId());
    }

}
