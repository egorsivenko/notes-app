package org.example.todolist.service;

import org.example.todolist.entity.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    List<Note> listAll();

    Note getById(UUID id);

    Note add(Note note);

    void update(Note note);

    void deleteById(UUID id);
}
