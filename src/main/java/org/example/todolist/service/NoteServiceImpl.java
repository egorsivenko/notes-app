package org.example.todolist.service;

import org.example.todolist.entity.Note;
import org.example.todolist.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private static final String EXC_FORMAT = "Note with id '%s' does not exist";

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> listAll() {
        return noteRepository.findAllInCreationOrder();
    }

    @Override
    public Note getById(UUID id) {
        return noteRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(EXC_FORMAT, id)));
    }

    @Override
    public Note add(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void update(Note note) {
        UUID id = note.getId();
        checkNoteExistsById(id);
        noteRepository.updateNoteById(id, note.getTitle(), note.getContent());
    }

    @Override
    public void deleteById(UUID id) {
        checkNoteExistsById(id);
        noteRepository.deleteById(id);
    }

    private void checkNoteExistsById(UUID id) {
        if (!noteRepository.existsById(id)) {
            throw new NoSuchElementException(String.format(EXC_FORMAT, id));
        }
    }
}
