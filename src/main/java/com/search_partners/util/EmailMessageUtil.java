package com.search_partners.util;

import com.search_partners.model.User;
import com.search_partners.to.EmailMessage;

import java.util.HashMap;
import java.util.Map;

public class EmailMessageUtil {

    private EmailMessageUtil() {};

    public static EmailMessage getRegisterMail(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        return EmailMessage.builder()
                .to(user.getEmail())
                .subject("Пожалуйста, подтвердите Вашу электронную почту")
                .templateLocation("verification_email")
                .context(params)
                .build();
    }

}
