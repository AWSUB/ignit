package com.ignit.internship.exception;

public class MissingValueException extends Exception {
    
    public MissingValueException(String msg) {
        super(msg);
    }

    public MissingValueException(String msg, Throwable cause) {
        super(msg, cause);
    }  
}
