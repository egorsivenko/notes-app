package org.example.todolist.service;

import org.example.todolist.entity.Note;
import org.example.todolist.exception.NoteNotFoundException;
import org.example.todolist.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

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
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @Override
    public Note add(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void update(UUID id, Note note) {
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
            throw new NoteNotFoundException(id);
        }
    }
}
