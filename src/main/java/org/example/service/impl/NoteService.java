package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Note;
import org.example.model.User;
import org.example.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> findByAuthor(User author) {
        return noteRepository.findByAuthor(author);
    }

    public List<Note> findPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    public List<Note> findPublicNotesByAuthor(User author) {
        return noteRepository.findPublicNotesByAuthor(author);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public Note getById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}
