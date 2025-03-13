package com.ignit.internship.exception;

public class WrongMediaTypeException extends Exception {

    public WrongMediaTypeException(String msg) {
        super(msg);
    }

    public WrongMediaTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
