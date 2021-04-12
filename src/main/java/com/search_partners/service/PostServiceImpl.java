package com.search_partners.service;

import com.search_partners.model.Post;
import com.search_partners.model.User;
import com.search_partners.repository.PostRepository;
import com.search_partners.repository.UserRepository;
import com.search_partners.to.PostDto;
import com.search_partners.util.DateUtil;
import com.search_partners.util.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Post> getPosts(int page) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, 100, Sort.by("date").descending()));
        posts.forEach(DateUtil::getDuration);
        return posts;
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
        return post;
    }

    @Override
    @Transactional
    public void savePost(PostDto postDto, Long id) {
        String text = PostUtil.validateText(postDto.getText());
        //TODO: Add check exception if user equals null
        User user = userRepository.findById(id).orElse(null);
        Post post = PostUtil.createNewFromTo(postDto);
        post.setUser(user);
        post.setText(text);
        postRepository.save(post);
    }
}