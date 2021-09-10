package com.search_partners.service;

import com.search_partners.model.*;
import com.search_partners.repository.CityRepository;
import com.search_partners.repository.CountryRepository;
import com.search_partners.repository.PostRepository;
import com.search_partners.repository.UserRepository;
import com.search_partners.to.PostDto;
import com.search_partners.util.DateUtil;
import com.search_partners.util.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           CountryRepository countryRepository, CityRepository cityRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Page<Post> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 100, Sort.by("date").descending());
        Page<Post> posts = postRepository.findAllByActive(1, pageable);
        posts.forEach(DateUtil::getDuration);
        return posts;
    }

    @Override
    public Page<Post> getPostsWithFilters(String countryName, String cityName, int page) {
        //if (Objects.isNull(country) || Objects.isNull(city) || country.isEmpty() || city.isEmpty())
        //TODO: exception if null country or city
        Page<Post> posts = Page.empty();
        Pageable pageable = PageRequest.of(page, 100, Sort.by("date").descending());
        Country country = countryRepository.findByNameEn(countryName).orElse(null);
        City city = cityRepository.findByNameEn(cityName).orElse(null);
        if (Objects.nonNull(country) && Objects.nonNull(city)) {
            if (!"any".equals(country.getNameEn()) && "any".equals(city.getNameEn()))
                posts = postRepository.findAllByCountryAndActive(country, 1, pageable);
            else if (!"any".equals(country.getNameEn()) && !"any".equals(city.getNameEn()))
                posts = postRepository.findAllByCountryAndCityAndActive(country, city, 1, pageable);
            else if ("any".equals(country.getNameEn()) && "any".equals(city.getNameEn()))
                posts = getPosts(page);
        }
        posts.forEach(DateUtil::getDuration);
        return posts;
    }

    @Override
    public Post getPostWithOwner(Long id, Long userId) {
        Post post = postRepository.findByIdAndUser(id, userId).orElse(null);
        if (Objects.isNull(post))
            System.out.println(""); //TODO: 404 page if no found
        return post;
    }

    @Override
    public List<Post> getAllPosts(Long id) {
        return PostUtil.prepareText(postRepository.findAllByUser(id));
    }

    @Override
    public Post getPost(long id) {
        //TODO: Add check exception if post equals null
        return postRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Post getPostWithComments(long id) {
        //TODO: Add check exception if post equals null
        Post post = postRepository.getPostWithComments(id).orElse(null);
        post.setShow(post.getShow() + 1);
        postRepository.save(post);
        Collections.sort(post.getCommentList());
        for (Comment comment : post.getCommentList()) {
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
    public boolean activePost(Long postId, Long userId) {
        return activeOrClose(postId, userId, 1);
    }

    private boolean activeOrClose(Long postId, Long userId, int active) {
        Post post = postRepository.findByIdAndUser(postId, userId).orElse(null);
        if (Objects.nonNull(post)) {
            post.setActive(active);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deletePost(Long postId, Long userId) {
        Post post = postRepository.findByIdAndUser(postId, userId).orElse(null);
        if (Objects.nonNull(post)) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void savePost(PostDto postDto, Long id) {
        Country country = countryRepository.findById(postDto.getCountry()).orElse(null);
        City city = cityRepository.findById(postDto.getCity()).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (Objects.isNull(country) || Objects.isNull(city) || Objects.isNull(user))
            System.out.println(""); //TODO: Add check exception if user equals null
        String text = PostUtil.validateText(postDto.getText());
        Post post = PostUtil.createNewFromTo(postDto, user, country, city);
        post.setText(text);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void editPost(PostDto postDto, Long id) {
        Post post = postRepository.findByIdAndUser(postDto.getId(), id).orElse(null);
        Country country = countryRepository.findById(postDto.getCountry()).orElse(null);
        City city = cityRepository.findById(postDto.getCity()).orElse(null);
        if (Objects.isNull(country) || Objects.isNull(city) || Objects.isNull(post))
            System.out.println("Exception"); //TODO: Add check exception if user equals null
        String text = PostUtil.validateText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setText(text);
        post.setCountry(country);
        post.setCity(city);
        postRepository.save(post);
    }
}