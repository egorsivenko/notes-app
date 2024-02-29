package org.example.todolist.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EditNoteForm extends NoteForm {

    private UUID id;

    public EditNoteForm() {}

    public EditNoteForm(String title, String content, UUID id) {
        super(title, content);
        this.id = id;
    }
}
