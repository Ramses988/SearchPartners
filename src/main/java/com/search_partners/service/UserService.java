package com.search_partners.service;

import com.search_partners.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUser(long id);

}
