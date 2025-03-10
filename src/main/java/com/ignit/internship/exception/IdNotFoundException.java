package com.ignit.internship.exception;

public class IdNotFoundException extends Exception {

    public IdNotFoundException(String msg) {
        super(msg);
    }

    public IdNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
