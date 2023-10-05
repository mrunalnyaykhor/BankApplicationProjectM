package com.bankmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private Object message;
    private HttpStatus status;
}
