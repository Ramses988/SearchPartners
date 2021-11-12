package com.search_partners.service;

import com.search_partners.model.Provider;
import com.search_partners.model.User;
import com.search_partners.service.interfaces.CountryAndCityService;
import com.search_partners.service.interfaces.OAuth2Service;
import com.search_partners.service.interfaces.UserService;
import com.search_partners.to.ClientOAuth2Request;
import com.search_partners.to.ClientOAuth2Response;
import com.search_partners.to.UserOAuth2;
import com.search_partners.util.OAuth2Params;
import com.search_partners.util.UserUtil;
import com.search_partners.util.exception.ErrorInternalException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
@AllArgsConstructor
public class OAuth2ServiceImpl implements OAuth2Service {

    private final OAuth2Params params;
    private final RestTemplate restTemplate;
    private final UserService service;
    private final CountryAndCityService countryAndCityService;

    @Override
    @Transactional
    public UsernamePasswordAuthenticationToken getUser(String code, Provider provider) {
        if (Objects.isNull(code) || code.isEmpty())
            throw new ErrorInternalException("Ошибка получения кода для oauth2 провайдер " + provider.getName());

        ClientOAuth2Response clientResponse = getToken(code, provider);

        if (Objects.isNull(clientResponse) || Objects.isNull(clientResponse.getAccessToken()) || clientResponse.getAccessToken().isEmpty())
            throw new ErrorInternalException("Ошибка получения токена для oauth2 " + provider.getName());

        User user;
        UserOAuth2 userInfo;

        if (Provider.GOOGLE.getName().equals(provider.getName())) {
            userInfo = restTemplate.getForObject("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token={access_token}",
                    UserOAuth2.class, clientResponse.getAccessToken());

            if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getEmail()) || userInfo.getEmail().isEmpty())
                throw new ErrorInternalException("Ошибка получения пользователя google oauth2");

            user = getUser(userInfo.getEmail(), clientResponse.getAccessToken(), provider);
        } else {
            user = getUser(clientResponse.getEmail(), clientResponse.getAccessToken(), provider);
        }

        return new UsernamePasswordAuthenticationToken(user.getEmail(), clientResponse.getAccessToken());

    }

    private User getUser(String email, String password, Provider provider) {
        User user = service.getUserWithProvider(email.toLowerCase(), provider.getName());

        if (Objects.nonNull(user))
            user.setPassword(UserUtil.prepareToPassword(password, service.getPasswordEncoder()));
        else{
            user = UserUtil.createUserFromOAuth2(email, password, provider.getName(), service.getPasswordEncoder());
            user.setCountry(countryAndCityService.getCountry(0));
            user.setCity(countryAndCityService.getCity(0));
        }

        return service.saveUser(user);
    }

    private ClientOAuth2Response getToken(String code, Provider provider) {
        if (Provider.GOOGLE.getName().equals(provider.getName())) {
            ClientOAuth2Request clientRequest = ClientOAuth2Request.builder()
                    .clientId(params.getGoogleId())
                    .clientSecret(params.getGoogleSecret())
                    .redirectURI(params.getGoogleRedirect())
                    .grantType("authorization_code")
                    .code(code)
                    .build();
            return restTemplate.postForObject("https://accounts.google.com/o/oauth2/token", clientRequest, ClientOAuth2Response.class);
        }

        if (Provider.VK.getName().equals(provider.getName())) {
            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("client_id", params.getVkId());
            requestParams.put("client_secret", params.getVkSecret());
            requestParams.put("code", code);
            requestParams.put("redirect_uri", params.getVkRedirect());
            return restTemplate.getForObject("https://oauth.vk.com/access_token?client_id={client_id}&client_secret={client_secret}&" +
                            "code={code}&redirect_uri={redirect_uri}",
                    ClientOAuth2Response.class, requestParams);
        }

        return null;
    }

}
