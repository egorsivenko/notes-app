package org.example.todolist.service;

import org.example.todolist.entity.Note;
import org.example.todolist.exception.InsufficientPrivilegesException;
import org.example.todolist.exception.NoteNotFoundException;
import org.example.todolist.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteServiceImpl(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    @Override
    public List<Note> listAll() {
        String username = userService.getCurrentUsername();
        return noteRepository.findByUsernameInCreationOrder(username);
    }

    @Override
    public Note getById(UUID id) {
        return verifyAndAccessNoteIfExists(id);
    }

    @Override
    public Note add(Note note) {
        String username = userService.getCurrentUsername();
        note.setUser(userService.getByUsername(username));
        return noteRepository.save(note);
    }

    @Override
    public void update(UUID id, Note note) {
        verifyAndAccessNoteIfExists(id);
        noteRepository.updateNoteById(id, note.getTitle(), note.getContent());
    }

    @Override
    public void deleteById(UUID id) {
        verifyAndAccessNoteIfExists(id);
        noteRepository.deleteById(id);
    }

    private Note verifyAndAccessNoteIfExists(UUID id) {
        Note note = noteRepository
                .findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        String username = userService.getCurrentUsername();
        if (!userService.isCurrentUserAdmin() && !noteBelongsToUser(note, username)) {
            throw new InsufficientPrivilegesException(username);
        }
        return note;
    }

    private boolean noteBelongsToUser(Note note, String username) {
        return Objects.equals(note.getUser().getUsername(), username);
    }
}
