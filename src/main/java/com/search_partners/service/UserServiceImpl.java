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

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUserWithCity(long id) {
        User user = repository.getUserWithCity(id).orElse(null);
        //TODO: check if not found
        return user;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email.toLowerCase()).orElse(null);
        //TODO: check if not found

        return new AuthorizedUser(user);
    }
}