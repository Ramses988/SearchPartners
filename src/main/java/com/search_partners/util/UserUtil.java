package com.search_partners.util;

import com.search_partners.model.Role;
import com.search_partners.model.User;
import com.search_partners.to.UserRegisterDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class UserUtil {

    public static User createNewFromTo(UserRegisterDto newUser) {
        String initial = String.valueOf(newUser.getName().charAt(0)).toUpperCase();
        return new User(newUser.getName(), LocalDateTime.now(), newUser.getEmail().toLowerCase(), false, initial,
                "#cc33cc", newUser.getGender(), Role.ROLE_USER);
    }

    public static String prepareToPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}