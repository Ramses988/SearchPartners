package com.search_partners.service.interfaces;

import com.search_partners.model.SellBusiness;
import com.search_partners.to.SellPostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SellBusinessService {

    void savePost(SellPostDto sellPost,Long id);

    void savePost(SellBusiness sellBusiness);

    Page<SellBusiness> getPosts(int page);

    SellBusiness getPostById(Long id);

    List<SellBusiness> getAllPosts(Long id);

    SellBusiness getPostWithComments(long id);

    boolean closePost(Long postId, Long userId);

    boolean deletePost(Long postId, Long userId);

}
