package com.search_partners.service;

import com.search_partners.AuthorizedUser;
import com.search_partners.model.City;
import com.search_partners.model.Country;
import com.search_partners.model.User;
import com.search_partners.repository.CityRepository;
import com.search_partners.repository.CountryRepository;
import com.search_partners.repository.UserRepository;
import com.search_partners.to.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final CountryAndCityService service;

    @Autowired
    public UserServiceImpl(UserRepository repository, CountryAndCityService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public User getUserWithCity(long id) {
        User user = repository.getUserWithCity(id).orElse(null);
        //TODO: check if not found
        return user;
    }

    @Override
    @Transactional
    public void saveUserProfile(UserProfileDto userDto, long id) {
        User user = getUser(id);
        Country country = service.getCountry(userDto.getCountry());
        City city = service.getCity(userDto.getCity());
        //TODO: check if not found User, Country and City
        user.setRealName(userDto.getRealName());
        user.setBusyness(userDto.getBusyness());
        user.setCountry(country);
        user.setCity(city);
        user.setGender(userDto.getGender());
        user.setDay(userDto.getDay());
        user.setMonth(userDto.getMonth());
        user.setYear(userDto.getYear());
        repository.save(user);
    }

    private User getUser(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase()).orElse(null);
        //TODO: check if not found

        return new AuthorizedUser(user);
    }
}