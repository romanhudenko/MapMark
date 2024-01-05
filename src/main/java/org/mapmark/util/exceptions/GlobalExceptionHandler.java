package org.mapmark.util.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //todo add another handlers

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
//        ApiResponse apiResponse = new ApiResponse(
//                HttpStatus.NOT_FOUND, e.getTitleMessageCode(), "Error occurred at: " + request);
//        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
//    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExist(UserAlreadyExistException ex) {
        ApiResponse apiResponse = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//        ApiResponse apiResponse = new ApiResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Error occurred at: " + request);
//        return new ResponseEntity<>(apiResponse, new HttpHeaders(), apiResponse.getStatus());
//    }


    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }


}