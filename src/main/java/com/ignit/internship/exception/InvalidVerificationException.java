package com.ignit.internship.exception;

public class InvalidVerificationException extends Exception {

    public InvalidVerificationException(String msg) {
        super(msg);
    }

    public InvalidVerificationException(String msg, Throwable cause) {
        super(msg, cause);
    }   
}
