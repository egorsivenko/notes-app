package org.example.todolist.exception;

public class CategoryNameAlreadyExists extends RuntimeException {

    public static final String MESSAGE = "Category with name '%s' already exists";

    public CategoryNameAlreadyExists(String name) {
        super(String.format(MESSAGE, name));
    }
}
