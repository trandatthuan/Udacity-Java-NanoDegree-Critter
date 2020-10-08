package com.udacity.jdnd.course3.critter.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    //##
    //## Fields
    //##
    private HttpStatus status;
    private String message;
    private String debugMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    //##
    //## Constructors
    //##
    public ApiError () { this.timeStamp = LocalDateTime.now(); }

    public ApiError (HttpStatus status) {
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError (HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError (HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.timeStamp = LocalDateTime.now();
    }

    //##
    //## Getters and setters
    //##
    public HttpStatus getStatus() { return status; }

    public void setStatus(HttpStatus status) { this.status = status; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getDebugMessage() { return debugMessage; }

    public void setDebugMessage(String debugMessage) { this.debugMessage = debugMessage; }

    public LocalDateTime getTimeStamp() { return timeStamp; }

    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }
}
