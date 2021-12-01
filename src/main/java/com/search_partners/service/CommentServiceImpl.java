package com.search_partners.service;

import com.search_partners.model.*;
import com.search_partners.model.abstractentity.AbstractComment;
import com.search_partners.model.abstractentity.AbstractInternalComment;
import com.search_partners.repository.CommentRepository;
import com.search_partners.repository.CommentSellRepository;
import com.search_partners.repository.InternalCommentRepository;
import com.search_partners.repository.InternalCommentSellRepository;
import com.search_partners.service.interfaces.CommentService;
import com.search_partners.service.interfaces.PostService;
import com.search_partners.service.interfaces.SellBusinessService;
import com.search_partners.service.interfaces.UserService;
import com.search_partners.util.exception.ErrorCheckRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentSellRepository commentSellRepository;
    private final UserService userService;
    private final PostService postService;
    private final SellBusinessService sellBusinessService;
    private final InternalCommentRepository internalCommentRepository;
    private final InternalCommentSellRepository internalCommentSellRepository;

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public AbstractComment saveComment(Long postId, int category, String message, Long id) {
        User user = userService.getUser(id);
        log.info("Add comment for post " + postId + " and user " + user.getName());
        if (category == 1)
            return saveCommentPost(postId, message, user);
        else
            return saveCommentSell(postId, message, user);
    }

    private AbstractComment saveCommentPost(Long postId, String message, User user) {
        Post post = postService.getPost(postId);
        post.setComments(post.getComments() + 1);
        postService.savePost(post);
        return commentRepository.save(new Comment(message, LocalDateTime.now(), post, user));
    }

    private AbstractComment saveCommentSell(Long postId, String message, User user) {
        SellBusiness sellBusiness = sellBusinessService.getPostById(postId);
        sellBusiness.setComments(sellBusiness.getComments() + 1);
        sellBusinessService.savePost(sellBusiness);
        return commentSellRepository.save(new CommentSell(message, LocalDateTime.now(), sellBusiness, user));
    }

    @Override
    @Transactional
    public AbstractInternalComment saveCommentChildren(Long parent, Long children, int category, String message, Long id) {
        User user = userService.getUser(id);
        log.info("Add comment for comment child " + children + " and user " + user.getName());
        if (category == 1)
            return saveCommentChildrenPost(parent, children, message, user);
        else
            return saveCommentChildrenSell(parent, children, message, user);
    }

    private AbstractInternalComment saveCommentChildrenPost(Long parent, Long children, String message, User user) {
        Comment comment = commentRepository.findById(parent).orElse(null);
        if (Objects.isNull(user) || Objects.isNull(comment))
            throw new ErrorCheckRequestException("Возникла внутренняя ошибка сервера!");
        Post post = postService.getPost(comment.getPost().getId());
        if (children == 0) {
            //TODO: send mail parent
            message = String.format("<p>%s</p>", message);
        } else {
            //TODO: send mail children
            InternalComment internalComment = internalCommentRepository.findById(children).orElse(null);
            if (Objects.isNull(internalComment))
                throw new ErrorCheckRequestException("Возникла внутренняя ошибка сервера!");
            String getText = internalComment.getText();
            if (getText.length() > 270)
                getText = getText.substring(0, 190) + "...</p>";

            message = String.format("<div class='comment-response'><b>%s</b><br><em>%s</em></div><p>%s</p>",
                    internalComment.getUser().getName(), getText, message);
        }
        post.setComments(post.getComments() + 1);
        postService.savePost(post);
        return internalCommentRepository.save(new InternalComment(message, LocalDateTime.now(), comment, user));
    }

    private AbstractInternalComment saveCommentChildrenSell(Long parent, Long children, String message, User user) {
        CommentSell commentSell = commentSellRepository.findById(parent).orElse(null);
        if (Objects.isNull(user) || Objects.isNull(commentSell))
            throw new ErrorCheckRequestException("Возникла внутренняя ошибка сервера!");
        SellBusiness sellBusiness = sellBusinessService.getPostById(commentSell.getPost().getId());
        if (children == 0) {
            //TODO: send mail parent
            message = String.format("<p>%s</p>", message);
        } else {
            //TODO: send mail children
            InternalCommentSell internalCommentSell = internalCommentSellRepository.findById(children).orElse(null);
            if (Objects.isNull(internalCommentSell))
                throw new ErrorCheckRequestException("Возникла внутренняя ошибка сервера!");
            String getText = internalCommentSell.getText();
            if (getText.length() > 270)
                getText = getText.substring(0, 190) + "...</p>";

            message = String.format("<div class='comment-response'><b>%s</b><br><em>%s</em></div><p>%s</p>",
                    internalCommentSell.getUser().getName(), getText, message);
        }
        sellBusiness.setComments(sellBusiness.getComments() + 1);
        sellBusinessService.savePost(sellBusiness);
        return internalCommentSellRepository.save(new InternalCommentSell(message, LocalDateTime.now(), commentSell, user));
    }

}