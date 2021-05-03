package com.search_partners.service;

import com.search_partners.model.Comment;
import com.search_partners.model.Post;
import com.search_partners.model.User;
import com.search_partners.repository.CommentRepository;
import com.search_partners.util.PostUtil;
import com.search_partners.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, UserService userService, PostService postService) {
        this.repository = repository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public List<Comment> getComments() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Comment saveComment(Long postId, String message, Long id) {
        User user = userService.getUser(id);
        Post post = postService.getPost(postId);
        //TODO: check User and Post if not found
        post.setComments(post.getComments() + 1);
        postService.savePost(post);
        return repository.save(new Comment(message, LocalDateTime.now(), post, user));
    }
}