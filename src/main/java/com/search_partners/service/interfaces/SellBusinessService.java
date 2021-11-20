package com.search_partners.service.interfaces;

import com.search_partners.model.SellBusiness;
import com.search_partners.to.SellPostDto;
import org.springframework.data.domain.Page;

public interface SellBusinessService {

    void savePost(SellPostDto sellPost,Long id);

    Page<SellBusiness> getPosts(int page);

    SellBusiness getPostWithComments(long id);

}
