package com.search_partners.service.interfaces;

import com.search_partners.model.User;
import com.search_partners.to.ChangePasswordDto;
import com.search_partners.to.ContactDto;
import com.search_partners.to.UserProfileDto;
import com.search_partners.to.UserRegisterDto;
import com.search_partners.util.exception.ErrorNotFoundPageException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService {

    User getUserWithCity(long id);

    User getUser(long id);

    void saveUserProfile(UserProfileDto user, long id);

    void createUser(UserRegisterDto newUser);

    void changePassword(ChangePasswordDto request, long id);

    void resetPasswordEmail(String email);

    PasswordEncoder getPasswordEncoder();

    void sendMessageFromContact(ContactDto contact);

    void checkToken(String token) throws ErrorNotFoundPageException;

    String resetPassword(String token, String password, String confirmPassword);

    void activeUser(String token) throws ErrorNotFoundPageException;

}