package com.search_partners.service;

import com.search_partners.model.User;
import com.search_partners.to.UserProfileDto;
import com.search_partners.to.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService {

    User getUserWithCity(long id);

    void saveUserProfile(UserProfileDto user, long id);

    void createUser(UserRegisterDto newUser);

}
