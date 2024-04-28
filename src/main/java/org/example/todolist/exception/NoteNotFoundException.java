package org.example.todolist.exception;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Note with id '%s' cannot be found";

    public NoteNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
