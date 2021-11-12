package com.search_partners.util.handler;

import com.search_partners.util.exception.ErrorCheckRequestException;
import com.search_partners.util.exception.ErrorInternalException;
import com.search_partners.util.exception.ErrorNotFoundPageException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

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

    @ExceptionHandler(ErrorNotFoundPageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle(ErrorNotFoundPageException ex) {
        log.warn(ex.getLocalizedMessage());
        ModelAndView view = new ModelAndView();
        view.setViewName("error/404");
        return view;
    }

    @ExceptionHandler(ErrorInternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle(ErrorInternalException ex) {
        log.error(ex.getLocalizedMessage());
        ModelAndView view = new ModelAndView();
        view.setViewName("error/500");
        return view;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle(Exception ex) {
        log.error("Handle error Exception.class: Ошибка обработки запроса!" + ex.getLocalizedMessage());
        ModelAndView view = new ModelAndView();
        view.setViewName("error/500");
        return view;
    }

}
