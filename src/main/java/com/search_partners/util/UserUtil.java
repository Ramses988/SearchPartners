package com.search_partners.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.search_partners.model.Role;
import com.search_partners.model.User;
import com.search_partners.to.UserDto;
import com.search_partners.to.UserRegisterDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

public class UserUtil extends StdConverter<User, UserDto> {

    public static User createNewFromTo(UserRegisterDto newUser) {
        String initial = String.valueOf(newUser.getName().trim().charAt(0)).toUpperCase();
        return User.builder()
                .name(newUser.getName().trim())
                .date(LocalDateTime.now())
                .email(newUser.getEmail().toLowerCase().trim())
                .enabled(false)
                .initial(initial)
                .color("#cc33cc")
                .gender(newUser.getGender())
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }

    public static User getUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static String prepareToPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }


    @Override
    public UserDto convert(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .initial(user.getInitial())
                .color(user.getColor())
                .build();
    }
}