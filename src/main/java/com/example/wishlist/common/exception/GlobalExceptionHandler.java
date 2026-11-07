package com.example.wishlist.common.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidItem(InvalidItemException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(InvalidBudgetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidBudget(InvalidBudgetException e) {
        return Map.of("message", e.getMessage());
    }

    @ExceptionHandler(InvalidItemException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(InvalidItemException e) {
        return Map.of("message", e.getMessage());
    }

}
