package com.search_partners.service;

import com.search_partners.model.Post;
import com.search_partners.to.PostDto;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<Post> getPosts(int page);

    Post getPost(long id);

    void save(Post post);

    void savePost(PostDto postDto, Long id);

}