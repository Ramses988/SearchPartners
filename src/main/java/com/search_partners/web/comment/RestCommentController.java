package com.search_partners.web.comment;

import com.search_partners.model.Comment;
import com.search_partners.model.InternalComment;
import com.search_partners.service.CommentService;
import com.search_partners.util.CommentUtil;
import com.search_partners.util.SecurityUtil;
import com.search_partners.util.exception.ErrorCheckRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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

//    @GetMapping("/get-comments")
//    public List<Comment> getCmments() {
//        return service.getComments();
//    }

    //TODO: Close url
    @PostMapping("/save-comment")
    public Comment saveComment(Long post, String message) throws ErrorCheckRequestException {
        message = CommentUtil.commentValid(message);
        return service.saveComment(post, message, SecurityUtil.authUserId());
    }

    //TODO: Close url
    @PostMapping("/save-comment-children")
    public InternalComment saveCommentChildren(Long parent, Long children, String message) throws ErrorCheckRequestException {
        message = CommentUtil.commentValid(message);
        return service.saveCommentChildren(parent, children, message, SecurityUtil.authUserId());
    }
}
