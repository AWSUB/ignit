package com.ignit.internship.exception;

public class NonCreatorException extends Exception {

    public NonCreatorException(String msg) {
        super(msg);
    }

    public NonCreatorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
