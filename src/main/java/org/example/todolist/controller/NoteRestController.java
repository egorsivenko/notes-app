package org.example.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.todolist.controller.request.NoteRequest;
import org.example.todolist.controller.response.NoteResponse;
import org.example.todolist.entity.Note;
import org.example.todolist.mapper.NoteMapper;
import org.example.todolist.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Notes")
@RequestMapping("api/V2/notes")
public class NoteRestController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteRestController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Retrieve all notes")
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<Note> notes = noteService.listAll(true);
        return ResponseEntity.ok(noteMapper.toNoteResponses(notes));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get notes by category name")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<NoteResponse>> getNotesByCategory(@PathVariable("category") String categoryName) {
        List<Note> notes = noteService.getByCategoryName(categoryName);
        return ResponseEntity.ok(noteMapper.toNoteResponses(notes));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get note by ID")
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable("id") UUID id) {
        Note note = noteService.getById(id);
        return ResponseEntity.ok(noteMapper.toNoteResponse(note));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Create new note")
    @PostMapping
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NoteRequest noteRequest) {
        Note addedNote = noteService.add(noteMapper.toNoteEntity(noteRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteMapper.toNoteResponse(addedNote));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Modify note content")
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable("id") UUID id,
                                                   @Valid @RequestBody NoteRequest noteRequest) {
        Note updatedNote = noteService.update(id, noteMapper.toNoteEntity(noteRequest));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(noteMapper.toNoteResponse(updatedNote));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete note by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<NoteResponse> deleteNote(@PathVariable("id") UUID id) {
        Note deletedNote = noteService.deleteById(id);
        return ResponseEntity.ok(noteMapper.toNoteResponse(deletedNote));
    }
}
