package com.search_partners.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.search_partners.model.City;
import com.search_partners.model.Provider;
import com.search_partners.model.Role;
import com.search_partners.model.User;
import com.search_partners.to.UserDto;
import com.search_partners.to.UserOAuth2;
import com.search_partners.to.UserRegisterDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UserUtil extends StdConverter<User, UserDto> {
    
    private final static Map<Integer, String> colors = new HashMap<>();

    static {
        colors.put(1, "#cc33cc");
        colors.put(2, "#ffcc33");
        colors.put(3, "#cc0000");
        colors.put(4, "#00cc00");
        colors.put(5, "#4285f4");
        colors.put(6, "#0f9d58");
        colors.put(7, "#f4b400");
        colors.put(8, "#db4437");
        colors.put(9, "#2d3b41");
        colors.put(10, "#fb6a75");
        colors.put(11, "#00c7b7");
        colors.put(12, "#f6bc00");
        colors.put(13, "#f6d852");
        colors.put(14, "#8bc34a");
        colors.put(15, "#ffc107");
        colors.put(16, "#ff5722");
        colors.put(17, "#e91e63");
        colors.put(18, "#259b24");
        colors.put(19, "#cddc39");
        colors.put(20, "#9c27b0");
        colors.put(21, "#ffeb3b");
        colors.put(22, "#03a9f4");
        colors.put(23, "#00bcd4");
        colors.put(24, "#9e9e9e");
        colors.put(25, "#607d8b");
        colors.put(26, "#5677fc");
        colors.put(27, "#009688");
        colors.put(28, "#795548");
        colors.put(29, "#8ecae6");
        colors.put(30, "#219ebc");
        colors.put(31, "#023047");
    }

    public static User createNewFromTo(UserRegisterDto newUser) {
        String initial = getInitial(newUser.getName());
        return User.builder()
                .name(newUser.getName().trim())
                .date(LocalDateTime.now())
                .email(newUser.getEmail().toLowerCase().trim())
                .enabled(false)
                .initial(initial)
                .color(colors.get(getColor()))
                .gender(newUser.getGender())
                .provider(Provider.LOCAL.getName())
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }

    public static User createUserFromOAuth2(String id, String email, String password, String provider, PasswordEncoder passwordEncoder) {
        String initial = getInitial(provider);
        return User.builder()
                .userId(id)
                .name(provider)
                .date(LocalDateTime.now())
                .email((Objects.nonNull(email) ? email.toLowerCase().trim() : null))
                .enabled(true)
                .initial(initial)
                .gender("U")
                .password(prepareToPassword(password, passwordEncoder))
                .color(colors.get(getColor()))
                .provider(provider)
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }

    private static String getInitial(String name) {
        return String.valueOf(name.trim().charAt(0)).toUpperCase();
    }

    private static int getColor() {
        return new SecureRandom().nextInt(colors.size()) + 1;
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