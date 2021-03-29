package com.search_partners.util.handler;

import com.search_partners.util.exception.ErrorCheckRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCheckRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(ErrorCheckRequestException exception) {
        log.error("Handle error ErrorCheckRequestException.class: " + exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(BindException exception) {
        log.error("Handle error BindException.class: " + exception.getMessage());
        return exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("<br /> "));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(Exception exception) {
        log.error("Handle error Exception.class: Ошибка обработки запроса!");
        return "Ошибка обработки запроса!";
    }

}
