package com.recime.codingchallenge.exception;

public class InvalidSortDirectionException extends RuntimeException {
    public InvalidSortDirectionException(String direction) {
        super("Invalid sort direction: " + direction + ". Valid values are 'asc' or 'desc'.");
    }
}
