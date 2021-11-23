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
import java.util.List;
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
    public Page<SellBusiness> getPostsWithFilters(String countryName, String cityName, int page) {
        //if (Objects.isNull(country) || Objects.isNull(city) || country.isEmpty() || city.isEmpty())
        //TODO: exception if null country or city
        Page<SellBusiness> sell = Page.empty();
        Pageable pageable = PageRequest.of(page, 100, Sort.by("date").descending());
        Country country = countryRepository.findByNameEn(countryName).orElse(null);
        City city = cityRepository.findByNameEn(cityName).orElse(null);
        if (Objects.nonNull(country) && Objects.nonNull(city)) {
            if (!"any".equals(country.getNameEn()) && "any".equals(city.getNameEn()))
                sell = repository.findAllByCountryAndActive(country.getId(), pageable);
            else if (!"any".equals(country.getNameEn()) && !"any".equals(city.getNameEn()))
                sell = repository.findAllByCountryAndCityAndActive(country.getId(), city.getId(), pageable);
            else if ("any".equals(country.getNameEn()) && "any".equals(city.getNameEn()))
                sell = getPosts(page);
        }
        sell.forEach(DateUtil::getDuration);
        return sell;
    }

    @Override
    public void savePost(SellBusiness sellBusiness) {
        repository.save(sellBusiness);
    }

    @Override
    public SellBusiness getPostById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<SellBusiness> getAllPosts(Long id) {
        return PostUtil.prepareTextSell(repository.findAllByUser(id));
    }

    @Override
    public SellBusiness getPostWithOwner(Long id, Long userId) {
        SellBusiness sellBusiness = repository.findByIdAndUser(id, userId).orElse(null);
        if (Objects.isNull(sellBusiness))
            System.out.println(""); //TODO: 404 page if no found
        return sellBusiness;
    }

    @Override
    @Transactional
    public SellBusiness getPostWithComments(long id) {
        //TODO: Add check exception if post equals null
        SellBusiness post = repository.getPostWithComments(id).orElse(null);
        post.setShow(post.getShow() + 1);
        repository.save(post);
        Collections.sort(post.getCommentList());
        for (CommentSell comment : post.getCommentList()) {
            Collections.sort(comment.getInternalComments());
        }
        return post;
    }

    @Override
    @Transactional
    public boolean closePost(Long postId, Long userId) {
        return activeOrClose(postId, userId, 0);
    }

    @Override
    @Transactional
    public void editPost(SellPostDto sellPostDto, Long id) {
        SellBusiness sellBusiness= repository.findByIdAndUser(sellPostDto.getId(), id).orElse(null);
        Country country = countryRepository.findById(sellPostDto.getCountry()).orElse(null);
        City city = cityRepository.findById(sellPostDto.getCity()).orElse(null);
        if (Objects.isNull(country) || Objects.isNull(city) || Objects.isNull(sellBusiness))
            System.out.println("Exception"); //TODO: Add check exception if user equals null
        String text = PostUtil.validateText(sellPostDto.getText());
        sellBusiness.setTitle(sellPostDto.getTitle());
        sellBusiness.setText(text);
        sellBusiness.setAge(sellPostDto.getAge());
        sellBusiness.setPrice(PostUtil.convertAmount(sellPostDto.getPrice()));
        sellBusiness.setIncome(PostUtil.convertAmount(sellPostDto.getIncome()));
        sellBusiness.setProfit(PostUtil.convertAmount(sellPostDto.getProfit()));
        sellBusiness.setCountry(country);
        sellBusiness.setCity(city);
        repository.save(sellBusiness);
    }

    @Override
    @Transactional
    public boolean activePost(Long postId, Long userId) {
        return activeOrClose(postId, userId, 1);
    }

    private boolean activeOrClose(Long postId, Long userId, int active) {
        SellBusiness sellBusiness = repository.findByIdAndUser(postId, userId).orElse(null);
        if (Objects.nonNull(sellBusiness)) {
            sellBusiness.setActive(active);
            repository.save(sellBusiness);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deletePost(Long postId, Long userId) {
        SellBusiness sellBusiness = repository.findByIdAndUser(postId, userId).orElse(null);
        if (Objects.nonNull(sellBusiness)) {
            repository.delete(sellBusiness);
            return true;
        }
        return false;
    }
}
