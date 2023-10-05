package com.bankmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserTransactionException extends RuntimeException {
    public UserTransactionException(String message)
    {
        super(message);
    }

}
