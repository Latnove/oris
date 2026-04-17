package org.example.service.impl;

import org.example.model.Note;
import org.example.model.User;
import org.example.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void findByAuthor_shouldReturnNotes() {
        User user = new User();
        Note note = new Note();
        note.setAuthor(user);

        when(noteRepository.findByAuthor(user)).thenReturn(List.of(note));

        List<Note> result = noteService.findByAuthor(user);

        assertEquals(1, result.size());
        verify(noteRepository).findByAuthor(user);
    }

    @Test
    void findPublicNotes_shouldReturnPublicNotes() {
        Note note = new Note();
        note.setIsPublic(true);

        when(noteRepository.findByIsPublicTrue()).thenReturn(List.of(note));

        List<Note> result = noteService.findPublicNotes();

        assertFalse(result.isEmpty());
        verify(noteRepository).findByIsPublicTrue();
    }

    @Test
    void findPublicNotesByAuthor_shouldWork() {
        User user = new User();
        Note note = new Note();

        when(noteRepository.findPublicNotesByAuthor(user)).thenReturn(List.of(note));

        List<Note> result = noteService.findPublicNotesByAuthor(user);

        assertEquals(1, result.size());
        verify(noteRepository).findPublicNotesByAuthor(user);
    }

    @Test
    void save_shouldReturnSavedNote() {
        Note note = new Note();

        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.save(note);

        assertNotNull(result);
        verify(noteRepository).save(note);
    }

    @Test
    void getById_shouldReturnNote() {
        Note note = new Note();
        note.setId(1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Note result = noteService.getById(1L);

        assertEquals(1L, result.getId());
        verify(noteRepository).findById(1L);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> noteService.getById(1L));

        assertEquals("Note not found", exception.getMessage());
    }

    @Test
    void delete_shouldCallRepository() {
        Long id = 1L;

        noteService.delete(id);

        verify(noteRepository).deleteById(id);
    }
}