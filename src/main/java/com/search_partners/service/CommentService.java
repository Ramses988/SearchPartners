package com.search_partners.service;

import com.search_partners.model.Comment;
import com.search_partners.model.InternalComment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments();

    Comment saveComment(Long postId, String message, Long id);

    InternalComment saveCommentChildren(Long parent, Long children, String message, Long id);

}
