package com.search_partners.service;

import com.search_partners.model.Comment;
import com.search_partners.model.InternalComment;
import com.search_partners.model.Post;
import com.search_partners.model.User;
import com.search_partners.repository.CommentRepository;
import com.search_partners.repository.InternalCommentRepository;
import com.search_partners.util.PostUtil;
import com.search_partners.util.UserUtil;
import com.search_partners.util.exception.ErrorCheckRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;
    private final PostService postService;
    private final InternalCommentRepository internalCommentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, UserService userService, PostService postService,
                              InternalCommentRepository internalCommentRepository) {
        this.repository = repository;
        this.userService = userService;
        this.postService = postService;
        this.internalCommentRepository = internalCommentRepository;
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

    @Override
    @Transactional
    public InternalComment saveCommentChildren(Long parent, Long children, String message, Long id) {
        User user = userService.getUser(id);
        Comment comment = repository.findById(parent).orElse(null);
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
            //TODO: cut if lenth message more than 200 charects
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
}