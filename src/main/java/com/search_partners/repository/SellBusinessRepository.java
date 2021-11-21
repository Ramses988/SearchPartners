package com.search_partners.repository;

import com.search_partners.model.SellBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SellBusinessRepository extends JpaRepository<SellBusiness, Long> {

    Page<SellBusiness> findAllByActive(int active, Pageable pageable);

    @EntityGraph(attributePaths = {"commentList"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT s FROM SellBusiness s WHERE s.id=:id")
    Optional<SellBusiness> getPostWithComments(@Param("id") long id);

    @Query("SELECT s FROM SellBusiness s WHERE s.user.id=:id ORDER BY s.date")
    List<SellBusiness> findAllByUser(Long id);

    @Query("SELECT s FROM SellBusiness s WHERE s.id=:postId and s.user.id=:userId")
    Optional<SellBusiness> findByIdAndUser(Long postId, Long userId);

}
