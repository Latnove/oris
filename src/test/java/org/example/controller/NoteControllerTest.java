package org.example.controller;

import org.example.config.SecurityConfig;
import org.example.model.Note;
import org.example.model.User;
import org.example.service.impl.NoteService;
import org.example.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@Import(SecurityConfig.class)
class NoteControllerTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUserNotes() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("bulka");

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);
        note.setTitle("test");
        note.setContent("content");
        note.setIsPublic(false);

        when(userService.findByUsername("bulka")).thenReturn(user);
        when(noteService.findByAuthor(user)).thenReturn(List.of(note));

        mockMvc.perform(get("/notes")
                        .with(user("bulka")))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(model().attributeExists("notes"));
    }

    @Test
    void shouldReturnEditForm_whenUserIsOwner() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("bulka");

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);

        when(userService.findByUsername("bulka")).thenReturn(user);
        when(noteService.getById(1L)).thenReturn(note);

        mockMvc.perform(get("/notes/1/edit")
                        .with(user("bulka")))
                .andExpect(status().isOk())
                .andExpect(view().name("note_form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void shouldReturnPublicNotes() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("bulka");

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);
        note.setTitle("test");
        note.setContent("content");
        note.setIsPublic(true);

        when(noteService.findPublicNotes()).thenReturn(List.of(note));

        mockMvc.perform(get("/notes/public"))
                .andExpect(status().isOk())
                .andExpect(view().name("public_notes"))
                .andExpect(model().attributeExists("notes"));
    }

    @Test
    void shouldReturnCreateForm() throws Exception {
        mockMvc.perform(get("/notes/create")
                        .with(user("bulka")))
                .andExpect(status().isOk())
                .andExpect(view().name("note_form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void shouldCreateNote() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("bulka");

        when(userService.findByUsername("bulka")).thenReturn(user);

        mockMvc.perform(post("/notes/create")
                        .with(user("bulka"))
                        .with(csrf())
                        .param("title", "Test")
                        .param("content", "Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));

        verify(noteService).save(any(Note.class));
    }

    @Test
    void shouldEditNote() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("bulka");

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);
        note.setTitle("test");
        note.setContent("content");
        note.setIsPublic(false);

        when(userService.findByUsername("bulka")).thenReturn(user);
        when(noteService.getById(1L)).thenReturn(note);

        mockMvc.perform(post("/notes/1/edit")
                        .with(user("bulka"))
                        .with(csrf())
                        .param("title", "New")
                        .param("content", "Updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));

        verify(noteService).save(note);
    }

    @Test
    void shouldFailEditIfNotOwner() throws Exception {
        User owner = new User();
        owner.setId(1L);

        User anotherUser = new User();
        anotherUser.setId(2L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(owner);
        note.setTitle("test");
        note.setContent("content");
        note.setIsPublic(false);

        when(userService.findByUsername("bulka")).thenReturn(anotherUser);
        when(noteService.getById(1L)).thenReturn(note);

        mockMvc.perform(post("/notes/1/edit")
                        .with(user("bulka"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteNote() throws Exception {
        User user = new User();
        user.setId(1L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);
        note.setTitle("test");
        note.setContent("content");
        note.setIsPublic(true);

        when(userService.findByUsername("bulka")).thenReturn(user);
        when(noteService.getById(1L)).thenReturn(note);

        mockMvc.perform(post("/notes/1/delete")
                        .with(user("bulka"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));

        verify(noteService).delete(1L);
    }
}