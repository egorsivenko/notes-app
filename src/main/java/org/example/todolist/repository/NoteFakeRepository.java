package org.example.todolist.repository;

import org.example.todolist.entity.Note;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Repository
public class NoteFakeRepository {

    private final List<Note> notes = new ArrayList<>();

    private static final String EXC_FORMAT = "Note with id '%s' does not exist";

    public List<Note> getAllNotes() {
        return notes;
    }

    public Note getNoteById(UUID id) {
        return notes.stream()
                .filter(note -> Objects.equals(note.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format(EXC_FORMAT, id)));
    }

    public Note addNote(Note note) {
        note.setId(UUID.randomUUID());
        note.setLastUpdatedOn(LocalDateTime.now());
        this.notes.add(note);
        return note;
    }

    public void updateNote(Note note) {
        UUID id = note.getId();

        notes.stream()
                .filter(n -> Objects.equals(n.getId(), id))
                .findFirst()
                .ifPresentOrElse(n -> {
                    int index = notes.indexOf(n);
                    note.setLastUpdatedOn(LocalDateTime.now());
                    notes.set(index, note);
                }, () -> {
                    throw new NoSuchElementException(String.format(EXC_FORMAT, id));
                });
    }

    public void deleteNote(UUID id) {
        boolean wasRemoved = notes.removeIf(note -> Objects.equals(note.getId(), id));
        if (!wasRemoved) {
            throw new NoSuchElementException(String.format(EXC_FORMAT, id));
        }
    }
}
