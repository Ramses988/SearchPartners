package com.search_partners.web.account;

import com.search_partners.model.Provider;
import com.search_partners.service.interfaces.OAuth2Service;
import com.search_partners.util.OAuth2Params;
import com.search_partners.util.exception.ErrorInternalException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Log4j2
@Controller
@AllArgsConstructor
public class OAuth2Controller {

    private final OAuth2Params params;
    private final OAuth2Service service;
    private final AuthenticationManager authenticationManager;
    private final PersistentTokenBasedRememberMeServices rememberMeServices;

    @GetMapping("/oauth2/provider/google")
    public String getLinkGoogle() {
        return String.format("redirect:https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
                params.getGoogleId(), params.getGoogleRedirect(), params.getGoogleScope());
    }

    @GetMapping("/oauth2/provider/vk")
    public String getLinkVK() {
        return String.format("redirect:https://oauth.vk.com/authorize?client_id=%s&redirect_uri=%s&v=6.3&response_type=code&scope=%s",
                params.getVkId(), params.getVkRedirect(), params.getVkScope());
    }

    @GetMapping("/oauth2/code/google")
    public String getUserGoogle(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
        return getUser(code, Provider.GOOGLE, request, response);
    }

    @GetMapping("/oauth2/code/vk")
    public String getUserVK(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
        return getUser(code, Provider.VK, request, response);
    }

    private String getUser(String code, Provider provider, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authToken = service.getUser(code, provider);

        try {
            authToken.setDetails(new WebAuthenticationDetails(request));

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            rememberMeServices.loginSuccess(request, response, authentication);
        }
        catch (Exception ex) {
            throw new ErrorInternalException("Ошибка аунтефикации пользователя " + provider.getName());
        }

        return "redirect:/";
    }

}