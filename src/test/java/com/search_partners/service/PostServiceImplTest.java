package com.search_partners.service;

import com.search_partners.model.Post;
import com.search_partners.service.interfaces.PostService;
import com.search_partners.util.exception.ErrorNotFoundPageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PostServiceImplTest {

    @Autowired
    public PostService service;

    @Test
    public void getPostsTest() {
        Page<Post> posts = service.getPosts(0);
        Assertions.assertNotNull(posts);
        Assertions.assertTrue(posts.getSize() > 0);
    }

    @Test()
    public void getPostWithOwnerTest() {
        Post post = service.getPostWithOwner(1L, 1L);
        Assertions.assertNotNull(post);
        Assertions.assertEquals("vas.tvk7619", post.getUser().getName());
    }

    @Test()
    public void getPostWithOwnerWithExceptionTestException() {
        assertThrows(ErrorNotFoundPageException.class, () ->
                service.getPostWithOwner(15L, 15L));
    }

}