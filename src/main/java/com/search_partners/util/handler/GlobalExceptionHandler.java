package com.search_partners.util.handler;

import com.search_partners.util.exception.ErrorCheckRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCheckRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ErrorCheckRequestException exception) {
        // TODO: Add log
        return exception.getMessage();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(BindException exception) {
        // TODO: Add log
        return exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("<br /> "));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(Exception exception) {
        // TODO: Add log
        return "Ошибка обработки запроса!";
    }

}
