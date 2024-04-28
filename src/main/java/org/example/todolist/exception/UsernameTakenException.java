package org.example.todolist.exception;

public class UsernameTakenException extends RuntimeException {

    private static final String MESSAGE = "Username is already taken: %s";

    public UsernameTakenException(String username) {
        super(String.format(MESSAGE, username));
    }
}
