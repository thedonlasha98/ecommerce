package com.ecommerce.ecommerceWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceException {
    @ExceptionHandler
    public ResponseEntity<?> handleException(GeneralException e){
        return new ResponseEntity<>(new GeneralExceptionResponse(e.getErrorCode().getDescription()), HttpStatus.BAD_REQUEST);
    }
}
