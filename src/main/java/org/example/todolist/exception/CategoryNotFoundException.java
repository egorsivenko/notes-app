package org.example.todolist.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    private static final String EXC_FORMAT = "Category with id '%s' cannot be found";

    public CategoryNotFoundException(UUID id) {
        super(String.format(EXC_FORMAT, id));
    }
}
