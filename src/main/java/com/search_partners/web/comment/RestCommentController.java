package com.search_partners.web.comment;

import com.search_partners.model.InternalComment;
import com.search_partners.model.abstractentity.AbstractComment;
import com.search_partners.model.abstractentity.AbstractInternalComment;
import com.search_partners.service.interfaces.CommentService;
import com.search_partners.util.CommentUtil;
import com.search_partners.util.SecurityUtil;
import com.search_partners.util.exception.ErrorCheckRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = RestCommentController.REST_URL)
public class RestCommentController {

    static final String REST_URL = "/rest/comments";
    private final CommentService service;

    @Autowired
    public RestCommentController(CommentService service) {
        this.service = service;
    }

    //TODO: Close url
    @PostMapping("/save-comment")
    public AbstractComment saveComment(Long post, int category, String message) throws ErrorCheckRequestException {
        message = CommentUtil.commentValid(message);
        return service.saveComment(post, category, message, SecurityUtil.authUserId());
    }

    //TODO: Close url
    @PostMapping("/save-comment-children")
    public AbstractInternalComment saveCommentChildren(Long parent, Long children, int category, String message) throws ErrorCheckRequestException {
        message = CommentUtil.commentValid(message);
        return service.saveCommentChildren(parent, children, category, message, SecurityUtil.authUserId());
    }
}
