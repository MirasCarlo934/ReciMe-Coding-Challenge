package com.recime.codingchallenge.controller;

import com.recime.codingchallenge.exception.InvalidSortDirectionException;
import com.recime.codingchallenge.exception.InvalidSortPropertyException;
import com.recime.codingchallenge.exception.RecipeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseBody handleRecipeNotFoundException(RecipeNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponseBody(ex.getMessage());
    }

    @ExceptionHandler({
            InvalidSortPropertyException.class,
            InvalidSortDirectionException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseBody handleSortingExceptions(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponseBody(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    public record ErrorResponseBody (String message) {}
}
