package com.ignit.internship.exception;

public class AlreadyBoughtException extends Exception {
    
    public AlreadyBoughtException(String msg) {
        super(msg);
    }

    public AlreadyBoughtException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
