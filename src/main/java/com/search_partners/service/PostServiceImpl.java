package com.search_partners.service;

import com.search_partners.model.Post;
import com.search_partners.repository.PostRepository;
import com.search_partners.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Post> getPosts(int page) {
        Page<Post> posts = repository.findAll(PageRequest.of(page, 100, Sort.by("date").descending()));
        posts.forEach(DateUtils::getDuration);
        return posts;
    }
}