package com.search_partners.service;

import com.search_partners.model.ConfirmToken;
import com.search_partners.model.User;
import com.search_partners.repository.ConfirmTokenRepository;
import com.search_partners.service.interfaces.ConfirmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmTokenServiceImpl implements ConfirmTokenService {

    private final ConfirmTokenRepository repository;

    @Autowired
    public ConfirmTokenServiceImpl(ConfirmTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public String newToken(User user) {
        ConfirmToken token = new ConfirmToken(user);
        repository.save(token);
        return token.getConfirmToken();
    }

    @Override
    public ConfirmToken activeUser(String token) {
        return repository.findByConfirmToken(token).orElse(null);
    }

    @Override
    public void removeToken(ConfirmToken token) {
        repository.delete(token);
    }
}
