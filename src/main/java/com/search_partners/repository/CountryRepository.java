package com.search_partners.repository;

import com.search_partners.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByNameEn(String nameEn);

}