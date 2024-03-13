package org.example.todolist.service;

import org.example.todolist.entity.Note;
import org.example.todolist.exception.InsufficientPrivilegesException;
import org.example.todolist.exception.NoteNotFoundException;
import org.example.todolist.repository.NoteRepository;
import org.example.todolist.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Note> listAll() {
        String username = getCurrentUsername();
        return noteRepository.findByUsernameInCreationOrder(username);
    }

    @Override
    public Note getById(UUID id) {
        return verifyNoteExistsAndCanBeAccessed(id);
    }

    @Override
    public Note add(Note note) {
        String username = getCurrentUsername();
        note.setUser(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        return noteRepository.save(note);
    }

    @Override
    public void update(UUID id, Note note) {
        verifyNoteExistsAndCanBeAccessed(id);
        noteRepository.updateNoteById(id, note.getTitle(), note.getContent());
    }

    @Override
    public void deleteById(UUID id) {
        verifyNoteExistsAndCanBeAccessed(id);
        noteRepository.deleteById(id);
    }

    private Note verifyNoteExistsAndCanBeAccessed(UUID id) {
        Note note = noteRepository
                .findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        String username = getCurrentUsername();
        if (!isCurrentUserAdmin() && !noteBelongsToUser(note, username)) {
            throw new InsufficientPrivilegesException(username);
        }
        return note;
    }

    private boolean isCurrentUserAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authy -> authy.getAuthority().equals("ADMIN"));
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    private boolean noteBelongsToUser(Note note, String username) {
        return Objects.equals(note.getUser().getUsername(), username);
    }
}
