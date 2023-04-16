// Import necessary classes and libraries
package com.ppk.locations.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// Declare a ControllerAdvice class for handling Location-related exceptions
@ControllerAdvice
public class LocationExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(LocationExceptionAdvice.class);

    // Handle LocationNotFoundException and respond with a NOT_FOUND status and the exception's message
    @ResponseBody
    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String locationNotFoundHandler(LocationNotFoundException ex) {
        logger.error("LocationNotFoundException: {}", ex.getMessage());
        return ex.getMessage();
    }

    // Handle LocationBlankEception and respond with a BAD_REQUEST status and the exception's message
    @ResponseBody
    @ExceptionHandler(LocationBlankEception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String locationNameBlankHandler(LocationBlankEception ex) {
        logger.error("LocationBlankEception: {}", ex.getMessage());
        return ex.getMessage();
    }
}
