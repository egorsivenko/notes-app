package org.example.todolist.exception;

public class UsernameTakenException extends RuntimeException {

    private static final String USERNAME_TAKEN_MESSAGE = "Username is already taken: %s";

    public UsernameTakenException(String username) {
        super(String.format(USERNAME_TAKEN_MESSAGE, username));
    }
}
