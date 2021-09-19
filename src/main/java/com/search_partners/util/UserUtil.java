package com.search_partners.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.search_partners.model.Role;
import com.search_partners.model.User;
import com.search_partners.to.UserDto;
import com.search_partners.to.UserRegisterDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserUtil extends StdConverter<User, UserDto> {
    
    private final static Map<Integer, String> colors = new HashMap<>();

    static {
        colors.put(1, "#cc33cc");
        colors.put(2, "#ffcc33");
        colors.put(3, "#cc0000");
        colors.put(4, "#00cc00");
    }

    public static User createNewFromTo(UserRegisterDto newUser) {
        String initial = String.valueOf(newUser.getName().trim().charAt(0)).toUpperCase();
        int color = new SecureRandom().nextInt(colors.size()) + 1;
        return User.builder()
                .name(newUser.getName().trim())
                .date(LocalDateTime.now())
                .email(newUser.getEmail().toLowerCase().trim())
                .enabled(false)
                .initial(initial)
                .color(colors.get(color))
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