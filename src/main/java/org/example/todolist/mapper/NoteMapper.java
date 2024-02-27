package org.example.todolist.mapper;

import org.example.todolist.controller.request.NoteRequest;
import org.example.todolist.controller.response.NoteResponse;
import org.example.todolist.entity.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {

    public Note toNoteEntity(NoteRequest noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        return note;
    }

    public NoteResponse toNoteResponse(Note note) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setTitle(note.getTitle());
        noteResponse.setContent(note.getContent());
        noteResponse.setLastUpdatedOn(note.getLastUpdatedOn());
        return noteResponse;
    }

    public List<NoteResponse> toNoteResponses(List<Note> notes) {
        return notes.stream()
                .map(this::toNoteResponse)
                .collect(Collectors.toList());
    }
}
