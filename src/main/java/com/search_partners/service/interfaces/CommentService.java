package com.search_partners.service.interfaces;

import com.search_partners.model.Comment;
import com.search_partners.model.InternalComment;
import com.search_partners.model.abstractentity.AbstractComment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments();

    AbstractComment saveComment(Long postId, int category, String message, Long id);

    InternalComment saveCommentChildren(Long parent, Long children, String message, Long id);

}
