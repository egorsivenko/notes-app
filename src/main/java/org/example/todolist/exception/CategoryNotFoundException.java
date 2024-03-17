package org.example.todolist.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    private static final String ID_MESSAGE = "Category with id '%s' cannot be found";
    private static final String NAME_MESSAGE = "Category with name '%s' cannot be found";

    public CategoryNotFoundException(UUID id) {
        super(String.format(ID_MESSAGE, id));
    }

    public CategoryNotFoundException(String name) {
        super(String.format(NAME_MESSAGE, name));
    }
}
