package com.search_partners.util;

import com.search_partners.model.User;
import com.search_partners.to.EmailMessage;

import java.util.HashMap;
import java.util.Map;

public class EmailMessageUtil {

    private EmailMessageUtil() {};

    public static EmailMessage getRegisterMail(User user, String token) {
        String subject = "Пожалуйста, подтвердите Вашу электронную почту";
        return generateMail(user, token, subject, "verification_email");
    }

    public static EmailMessage getResetPasswordMail(User user, String token) {
        return generateMail(user, token, "Сброс пароля", "reset-password");
    }

    private static EmailMessage generateMail(User user, String token, String subject, String nameTemplate) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("token", token);
        return EmailMessage.builder()
                .to(user.getEmail())
                .subject(subject)
                .templateLocation(nameTemplate)
                .context(params)
                .build();
    }

}