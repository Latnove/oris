package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/notes")
@RequiredArgsConstructor
public class AdminNoteController {

    private final NoteRepository noteRepository;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Long id) {
        noteRepository.deleteById(id);
    }
}