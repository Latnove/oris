package org.example.controller;

import org.example.model.Note;
import org.example.model.User;
import org.example.service.impl.NoteService;
import org.example.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("notes", noteService.findByAuthor(user));
        return "notes";
    }

    @GetMapping("/public")
    public String getPublicNotes(Model model) {
        model.addAttribute("notes", noteService.findPublicNotes());
        return "public_notes";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Note note, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        note.setAuthor(user);

        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        Note note = noteService.getById(id);

        if (!note.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Нет доступа");
        }

        model.addAttribute("note", note);
        return "note_form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,
                       @ModelAttribute Note updated,
                       Authentication auth) {

        User user = userService.findByUsername(auth.getName());
        Note note = noteService.getById(id);

        if (!note.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа");
        }

        note.setTitle(updated.getTitle());
        note.setContent(updated.getContent());
        note.setIsPublic(updated.getIsPublic());

        noteService.save(note);

        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        Note note = noteService.getById(id);

        if (!note.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Нет доступа");
        }

        noteService.delete(id);

        return "redirect:/notes";
    }
}
