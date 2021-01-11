package com.search_partners.service;

import com.search_partners.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<Post> getPosts(int page);

}