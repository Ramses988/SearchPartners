package com.search_partners.service;

import com.search_partners.model.Post;
import com.search_partners.to.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<Post> getPosts(int page);

    Post getPost(long id);

    Post getPostWithComments(long id);

    void savePost(Post post);

    void savePost(PostDto postDto, Long id);

    List<Post> getAllPosts(Long id);

    boolean closePost(Long postId, Long userId);

    boolean activePost(Long postId, Long userId);

    boolean deletePost(Long postId, Long userId);

}