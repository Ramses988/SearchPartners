package com.search_partners.service;

import com.search_partners.AuthorizedUser;
import com.search_partners.model.Role;
import com.search_partners.model.User;
import com.search_partners.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(long id) {
        User user = repository.findById(id).orElse(null);
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