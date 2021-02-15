package com.search_partners.service;

import com.search_partners.model.User;
import com.search_partners.to.UserProfileDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserWithCity(long id);

    void saveUserProfile(UserProfileDto user, long id);

}
