package com.search_partners.service;

import com.search_partners.model.PostEntity;
import com.search_partners.repository.PostRepository;
import com.search_partners.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PostEntity> getPosts() {
        List<PostEntity> posts = repository.findAll();
        posts.forEach(DateUtils::getDuration);
        return posts;
    }
}