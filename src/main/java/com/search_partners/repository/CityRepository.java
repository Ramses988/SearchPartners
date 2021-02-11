package com.search_partners.repository;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByCountry(Country country);

}