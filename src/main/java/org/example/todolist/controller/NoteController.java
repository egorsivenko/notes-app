package org.example.todolist.controller;

import org.example.todolist.entity.Note;
import org.example.todolist.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String getNotes(Model model) {
        model.addAttribute("notes", noteService.listAll());
        return "notes";
    }

    @GetMapping("/add")
    public String addNoteForm(Model model) {
        Note note = new Note();
        model.addAttribute("note", note);
        return "add_note";
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("note") Note note) {
        noteService.add(note);
        return "redirect:/api/notes/list";
    }

    @GetMapping("/edit/{id}")
    public String editNoteForm(@PathVariable("id") UUID id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PostMapping("/edit")
    public String editNote(@ModelAttribute("note") Note note) {
        noteService.update(note);
        return "redirect:/api/notes/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") UUID id) {
        noteService.deleteById(id);
        return "redirect:/api/notes/list";
    }
}
