package com.search_partners.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class OAuth2Params {

    @Value("${oauth2.google.clientId}")
    private String googleId;
    @Value("${oauth2.google.clientSecret}")
    private String googleSecret;
    @Value("${oauth2.google.scope}")
    private String googleScope;
    @Value("${oauth2.google.redirect}")
    private String googleRedirect;
    @Value("${oauth2.vk.clientId}")
    private String vkId;
    @Value("${oauth2.vk.clientSecret}")
    private String vkSecret;
    @Value("${oauth2.vk.scope}")
    private String vkScope;
    @Value("${oauth2.vk.redirect}")
    private String vkRedirect;

}
