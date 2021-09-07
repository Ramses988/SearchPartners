package com.search_partners.repository;

import com.search_partners.model.Post;
import com.search_partners.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    @EntityGraph(attributePaths = {"commentList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Post p WHERE p.id=:id")
    Optional<Post> getPostWithComments(@Param("id") long id);

    @Query("SELECT p FROM Post p WHERE p.user.id=:id")
    List<Post> findAllByUser(Long id);

}