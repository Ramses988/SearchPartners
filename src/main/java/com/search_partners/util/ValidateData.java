package com.search_partners.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {

    private static final String EMAIL_PATTERN = "^[^@]+@[^@.]+\\.[^@]+$";

    private ValidateData() {}

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}