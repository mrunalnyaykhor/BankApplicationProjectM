package com.bankmanagement.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(value={TransactionException.class})
    public ResponseEntity<ErrorDetails> handleUserTransactionException(TransactionException exception){
       ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
                                                                  ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors, HttpStatus.BAD_REQUEST);

        return  ResponseEntity.badRequest().body(errorDetails);
    }

}
