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

}
