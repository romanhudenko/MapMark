package org.mapmark.util.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;


@Getter
@Setter
public class ApiResponse {

    private int code;
    private HttpStatus status;
    private String message;


    public ApiResponse(int code, HttpStatus status, String message) {
        super();
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
