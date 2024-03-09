package org.mapmark.util.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String USER_EXIST = "User already exist";


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {


        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                VALIDATION_FAILED,
                errors);

        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExist(UserAlreadyExistException ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                USER_EXIST,
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFound(DataNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                DATA_NOT_FOUND,
                Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
    }

}