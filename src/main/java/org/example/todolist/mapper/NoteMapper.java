package org.example.todolist.mapper;

import org.example.todolist.controller.form.EditNoteForm;
import org.example.todolist.controller.form.NoteForm;
import org.example.todolist.controller.request.NoteRequest;
import org.example.todolist.controller.response.NoteResponse;
import org.example.todolist.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {

    public Note toNoteEntity(NoteForm noteForm) {
        Note note = new Note();
        note.setTitle(noteForm.getTitle());
        note.setContent(noteForm.getContent());
        return note;
    }

    public EditNoteForm toEditNoteForm(Note note) {
        EditNoteForm editNoteForm = new EditNoteForm();
        editNoteForm.setId(note.getId());
        editNoteForm.setTitle(note.getTitle());
        editNoteForm.setContent(note.getContent());
        return editNoteForm;
    }

    public Note toNoteEntity(NoteRequest noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        return note;
    }

    public NoteResponse toNoteResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getLastUpdatedOn()
        );
    }

    public List<NoteResponse> toNoteResponses(List<Note> notes) {
        return notes.stream()
                .map(this::toNoteResponse)
                .collect(Collectors.toList());
    }
}
