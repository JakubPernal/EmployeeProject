package com.pernal.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class EmployeeServiceResponse<T> implements Serializable {

    private T body;
    private String message;
    private HttpStatus status;

    private EmployeeServiceResponse(T body, String message, HttpStatus status) {
        this.body = body;
        this.message = message;
        this.status = status;
    }

    public static <T> EmployeeServiceResponse<T> createResponse(T body, HttpStatus status, String message) {
        return new EmployeeServiceResponse<>(body, message, status);
    }

    public static <T> EmployeeServiceResponse<T> emptyBodyResponse(HttpStatus status, String message) {
        return new EmployeeServiceResponse<>(null, message, status);
    }

    public T getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
