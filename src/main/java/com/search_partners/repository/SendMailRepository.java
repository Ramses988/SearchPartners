package com.search_partners.repository;

import com.search_partners.model.SendMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendMailRepository extends JpaRepository<SendMail, Long>  {
}
