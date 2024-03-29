package com.search_partners.service;

import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.repository.CityRepository;
import com.search_partners.repository.CountryRepository;
import com.search_partners.service.interfaces.CountryAndCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public List<City> getCitiesFromName(String name) {
        if (Objects.nonNull(name) && !name.isEmpty()) {
            Country country = countryRepository.findByNameEn(name).orElse(null);
            if (Objects.nonNull(country) && !"any".equals(country.getNameEn()))
                return cityRepository.findAllByCountry(country);
        }
        return List.of();
    }

    @Override
    public List<City> getCitiesFromNames(String name) {
        Country country = countryRepository.findByNameEn(name).orElse(null);
        if (Objects.nonNull(country))
            return getCities(country.getId());
        return List.of();
    }

    @Override
    public String getNameWhereSearch(String nameCountry, String nameCity) {
        City city = cityRepository.findByNameEn(nameCity).orElse(null);
        if (Objects.nonNull(city) && !"any".equals(city.getNameEn()))
            return city.getName();

        Country country = countryRepository.findByNameEn(nameCountry).orElse(null);
        if (Objects.nonNull(country) && !"any".equals(country.getNameEn()))
            return country.getName();

        return "";
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