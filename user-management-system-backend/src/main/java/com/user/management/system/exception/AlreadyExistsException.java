package com.user.management.system.exception;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException() {
        super("AlreadyExistsException");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
