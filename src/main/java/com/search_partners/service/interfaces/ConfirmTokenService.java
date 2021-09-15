package com.search_partners.service.interfaces;

import com.search_partners.model.ConfirmToken;
import com.search_partners.model.User;

public interface ConfirmTokenService {

    String newToken(User user, int type);

    ConfirmToken getToken(String token, int type);

    void removeToken(ConfirmToken token);

}
