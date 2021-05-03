package com.search_partners.service;

import com.search_partners.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments();

    Comment saveComment(Long postId, String message, Long id);

}
