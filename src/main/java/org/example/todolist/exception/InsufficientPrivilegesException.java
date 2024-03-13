package org.example.todolist.exception;

public class InsufficientPrivilegesException extends RuntimeException {

    private static final String MESSAGE = "Access denied for user: %s";

    public InsufficientPrivilegesException(String username) {
        super(String.format(MESSAGE, username));
    }
}
