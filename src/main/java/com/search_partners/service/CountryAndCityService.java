package com.search_partners.service;

import com.search_partners.model.City;
import com.search_partners.model.Country;

import java.util.List;

public interface CountryAndCityService {

    List<Country> getAllCountries();

    List<City> getCities(long id);

}
