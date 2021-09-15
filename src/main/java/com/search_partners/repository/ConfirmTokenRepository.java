package com.search_partners.repository;

import com.search_partners.model.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {

    Optional<ConfirmToken> findFirstByConfirmTokenAndType(String confirmToken, int type);

}