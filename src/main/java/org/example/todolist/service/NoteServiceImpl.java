package org.example.todolist.service;

import org.example.todolist.entity.Note;
import org.example.todolist.repository.NoteFakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteFakeRepository repository;

    public NoteServiceImpl(NoteFakeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Note> listAll() {
        return repository.getAllNotes();
    }

    @Override
    public Note getById(UUID id) {
        return repository.getNoteById(id);
    }

    @Override
    public Note add(Note note) {
        return repository.addNote(note);
    }

    @Override
    public void update(Note note) {
        repository.updateNote(note);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteNote(id);
    }
}
