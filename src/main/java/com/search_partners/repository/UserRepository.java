package com.search_partners.repository;

import com.search_partners.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmailAndEnabledAndProvider(String email, boolean enabled, String provider);

    Optional<User> findByUserIdAndProvider(String userId, String provider);

    Optional<User> findFirstByName(String name);

    @Query(value = "SELECT nextval('user_id_seq')", nativeQuery = true)
    Long getNextUserId();

    @EntityGraph(attributePaths = "city", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id=:id")
    Optional<User> getUserWithCity(long id);

}