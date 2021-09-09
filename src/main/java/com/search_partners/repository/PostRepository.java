package com.search_partners.repository;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.model.Post;
import com.search_partners.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Page<Post> findAllByActive(int active, Pageable pageable);

    Page<Post> findAllByCountryAndActive(Country country, int Active, Pageable pageable);

    Page<Post> findAllByCountryAndCityAndActive(Country country, City city, int Active, Pageable pageable);

    @EntityGraph(attributePaths = {"commentList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Post p WHERE p.id=:id")
    Optional<Post> getPostWithComments(@Param("id") long id);

    @Query("SELECT p FROM Post p WHERE p.user.id=:id ORDER BY p.date")
    List<Post> findAllByUser(Long id);

    @Query("SELECT p FROM Post p WHERE p.id=:postId and p.user.id=:userId")
    Optional<Post> findByIdAndUser(Long postId, Long userId);

}