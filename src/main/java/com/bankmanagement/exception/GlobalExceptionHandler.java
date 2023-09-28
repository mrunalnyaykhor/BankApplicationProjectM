package com.bankmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {CustomerException.class})
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(CustomerException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND);
        webRequest.getDescription(false);
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(value = {AccountException.class})
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(AccountException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND);
        webRequest.getDescription(false);
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(value = {BankException.class})
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(BankException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND);
        webRequest.getDescription(false);
        return ResponseEntity.ok(errorDetails);
    }
}
