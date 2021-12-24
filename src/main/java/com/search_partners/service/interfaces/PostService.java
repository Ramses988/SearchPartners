package com.search_partners.service.interfaces;

import com.search_partners.model.Post;
import com.search_partners.to.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<Post> getPosts(int page);

    Page<Post> getPopularPosts();

    Page<Post> getPostsWithFilters(String country, String city, int page);

    Post getPostWithOwner(Long id, Long userId);

    Post getPost(long id);

    Post getPostWithComments(long id);

    void savePost(Post post);

    void savePost(PostDto postDto, Long id);

    void editPost(PostDto postDto, Long id);

    List<Post> getAllPosts(Long id);

    boolean closePost(Long postId, Long userId);

    boolean activePost(Long postId, Long userId);

    boolean deletePost(Long postId, Long userId);

}