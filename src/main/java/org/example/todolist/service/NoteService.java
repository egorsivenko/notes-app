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
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<Note> listAll() {
        String username = userService.getCurrentUsername();
        return noteRepository.findByUsernameInCreationOrder(username);
    }

    public Note getById(UUID id) {
        Note note = noteRepository
                .findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        String username = userService.getCurrentUsername();
        if (userService.isCurrentUserNotAdmin() && !noteBelongsToUser(note, username)) {
            throw new InsufficientPrivilegesException(username);
        }
        return note;
    }

    public Note add(Note note) {
        note.setUser(userService.getCurrentUser());
        return noteRepository.save(note);
    }

    public Note update(UUID id, Note note) {
        Note noteById = getById(id);
        noteById.setTitle(note.getTitle());
        noteById.setContent(note.getContent());
        noteById.setCategory(note.getCategory());
        return noteRepository.save(noteById);
    }

    public Note deleteById(UUID id) {
        Note noteById = getById(id);
        noteRepository.delete(noteById);
        return noteById;
    }

    private boolean noteBelongsToUser(Note note, String username) {
        return Objects.equals(note.getUser().getUsername(), username);
    }
}
