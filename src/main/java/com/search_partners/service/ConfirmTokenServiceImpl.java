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
    public String newToken(User user, int type) {
        ConfirmToken token = new ConfirmToken(user, type);
        repository.save(token);
        return token.getConfirmToken();
    }

    @Override
    public ConfirmToken getToken(String token, int type) {
        return repository.findFirstByConfirmTokenAndType(token, type).orElse(null);
    }

    @Override
    public void removeToken(ConfirmToken token) {
        repository.delete(token);
    }
}
