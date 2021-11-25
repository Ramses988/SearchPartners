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

    public static String getSafeText(String message) {
        message = message.replace("<", "&lt;");
        message = message.replace(">", "&gt;");
        message = message.replace("/", "&frasl;");
        message = message.replace("'", "&prime;");
        message = message.replace("(", "&#40;");
        message = message.replace(")", "&#41;");
        message = message.replace("|", "&#124;");
        message = message.replace("&", "&amp;");
        message = message.replace("[", "");
        message = message.replace("]", "");
        return message;
    }

    public static String getRemoveChar(String message) {
        message = message.replace("<", "");
        message = message.replace(">", "");
        message = message.replace("/", "");
        message = message.replace("'", "");
        message = message.replace("|", "");
        message = message.replace("&", "");
        message = message.replace("[", "");
        message = message.replace("]", "");
        return message;
    }

}