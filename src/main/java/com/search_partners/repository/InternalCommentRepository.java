package com.search_partners.repository;

import com.search_partners.model.InternalComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalCommentRepository extends JpaRepository<InternalComment, Long> {
}