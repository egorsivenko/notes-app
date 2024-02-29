package org.example.todolist.controller;

import org.example.todolist.controller.form.AddNoteForm;
import org.example.todolist.controller.form.EditNoteForm;
import org.example.todolist.entity.Note;
import org.example.todolist.mapper.NoteMapper;
import org.example.todolist.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("api/V1/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @GetMapping("/list")
    public String getNotes(Model model) {
        model.addAttribute("notes", noteService.listAll());
        return "notes";
    }

    @GetMapping("/add")
    public String addNoteForm(Model model) {
        AddNoteForm addNoteForm = new AddNoteForm();
        model.addAttribute("addNoteForm", addNoteForm);
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("addNoteForm") AddNoteForm addNoteForm) {
        noteService.add(noteMapper.toNoteEntity(addNoteForm));
        return "redirect:/api/V1/notes/list";
    }

    @GetMapping("/edit")
    public String editNoteForm(@RequestParam("id") UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("editNoteForm", noteMapper.toEditNoteForm(note));
        return "edit_note";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute("editNoteForm") EditNoteForm editNoteForm) {
        noteService.update(editNoteForm.getId(), noteMapper.toNoteEntity(editNoteForm));
        return "redirect:/api/V1/notes/list";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam("id") UUID id) {
        noteService.deleteById(id);
        return "redirect:/api/V1/notes/list";
    }
}
