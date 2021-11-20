package com.search_partners.service;

import com.search_partners.model.*;
import com.search_partners.repository.CityRepository;
import com.search_partners.repository.CountryRepository;
import com.search_partners.repository.SellBusinessRepository;
import com.search_partners.repository.UserRepository;
import com.search_partners.service.interfaces.SellBusinessService;
import com.search_partners.to.SellPostDto;
import com.search_partners.util.DateUtil;
import com.search_partners.util.PostUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SellBusinessServiceImpl implements SellBusinessService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final SellBusinessRepository repository;

    @Override
    @Transactional
    public void savePost(SellPostDto sellPost, Long id) {
        Country country = countryRepository.findById(sellPost.getCountry()).orElse(null);
        City city = cityRepository.findById(sellPost.getCity()).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (Objects.isNull(country) || Objects.isNull(city) || Objects.isNull(user))
            System.out.println(""); //TODO: Add check exception if user equals null
        String text = PostUtil.validateText(sellPost.getText());
        SellBusiness sellBusiness = PostUtil.createSellBusinessNewFromTo(sellPost, user, country, city);
        sellBusiness.setText(text);
        repository.save(sellBusiness);
    }

    @Override
    public Page<SellBusiness> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.by("date").descending());
        Page<SellBusiness> posts = repository.findAllByActive(1, pageable);
        posts.forEach(DateUtil::getDuration);
        return posts;
    }

    @Override
    @Transactional
    public SellBusiness getPostWithComments(long id) {
        //TODO: Add check exception if post equals null
        SellBusiness post = repository.getPostWithComments(id).orElse(null);
        post.setShow(post.getShow() + 1);
        repository.save(post);
        Collections.sort(post.getCommentList());
        for (Comment comment : post.getCommentList()) {
            Collections.sort(comment.getInternalComments());
        }
        return post;
    }
}
