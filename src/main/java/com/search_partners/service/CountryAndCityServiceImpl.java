package com.search_partners.service;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.repository.CityRepository;
import com.search_partners.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CountryAndCityServiceImpl implements CountryAndCityService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @Autowired
    public CountryAndCityServiceImpl(CountryRepository countryRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<City> getCities(long id) {
        return cityRepository.findAllByCountry(new Country(id));
    }

    @Override
    public Country getCountry(long id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public City getCity(long id) {
        return cityRepository.findById(id).orElse(null);
    }
}