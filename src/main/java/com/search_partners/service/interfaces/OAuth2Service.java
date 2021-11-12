package com.search_partners.service.interfaces;

import com.search_partners.model.Provider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface OAuth2Service {

    UsernamePasswordAuthenticationToken getUser(String code, Provider provider);

}
