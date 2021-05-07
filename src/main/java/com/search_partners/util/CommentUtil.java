package com.search_partners.util;

import com.search_partners.util.exception.ErrorCheckRequestException;

import java.util.Objects;

public class CommentUtil {

    public static String commentValid(String message) {
        if (Objects.isNull(message) || message.isBlank())
            throw new ErrorCheckRequestException("Комментарий не должен быть пустым!");
        if (message.length() > 1000)
            throw new ErrorCheckRequestException("Максимальный размер поля, 1000 символов!");
        message = message.replace("<", "&lt;");
        message = message.replace(">", "&gt;");
        message = message.replace("/", "&frasl;");
        message = message.replace("'", "&prime;");
        return message;
    }

}
