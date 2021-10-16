package com.search_partners.service.interfaces;

import com.search_partners.model.City;
import com.search_partners.model.Country;

import java.util.List;

public interface CountryAndCityService {

    List<Country> getAllCountries();

    List<City> getCities(long id);

    List<City> getCitiesFromName(String name);

    List<City> getCitiesFromNames(String name);

    Country getCountry(long id);

    City getCity(long id);

    String getNameWhereSearch(String country, String city);

}
