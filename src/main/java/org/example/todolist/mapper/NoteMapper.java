package org.example.todolist.mapper;

import org.example.todolist.controller.form.NoteForm;
import org.example.todolist.controller.request.NoteRequest;
import org.example.todolist.controller.response.NoteResponse;
import org.example.todolist.entity.Note;
import org.example.todolist.service.CategoryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public NoteMapper(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    public Note toNoteEntity(NoteForm noteForm) {
        Note note = new Note();
        note.setTitle(noteForm.getTitle());
        note.setContent(noteForm.getContent());
        if (noteForm.getCategoryId() != null) {
            note.setCategory(categoryService.getById(noteForm.getCategoryId()));
        }
        return note;
    }

    public NoteForm toNoteForm(Note note) {
        NoteForm noteForm = new NoteForm();
        noteForm.setId(note.getId());
        noteForm.setTitle(note.getTitle());
        noteForm.setContent(note.getContent());
        if (note.getCategory() != null) {
            noteForm.setCategoryId(note.getCategory().getId());
        }
        return noteForm;
    }

    public Note toNoteEntity(NoteRequest noteRequest) {
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        if (noteRequest.getCategoryRequest() != null) {
            note.setCategory(categoryService.getByName(noteRequest.getCategoryRequest().getName()));
        }
        return note;
    }

    public NoteResponse toNoteResponse(Note note) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setTitle(note.getTitle());
        noteResponse.setContent(note.getContent());
        noteResponse.setLastUpdatedOn(note.getLastUpdatedOn());
        if (note.getCategory() != null) {
            noteResponse.setCategoryResponse(categoryMapper.toCategoryResponse(note.getCategory()));
        }
        return noteResponse;
    }

    public List<NoteResponse> toNoteResponses(List<Note> notes) {
        return notes.stream()
                .map(this::toNoteResponse)
                .collect(Collectors.toList());
    }
}
