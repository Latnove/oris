package org.example.controller;

import org.example.config.SecurityConfig;
import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminNoteController.class)
@Import(SecurityConfig.class)
class AdminNoteControllerTest {

    @MockitoBean
    private NoteRepository noteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllNotesForAdmin() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setContent("test note");

        when(noteRepository.findAll()).thenReturn(List.of(note));

        mockMvc.perform(get("/admin/notes")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].content").value("test note"));
    }

    @Test
    void shouldReturnForbiddenForNonAdmin() throws Exception {
        mockMvc.perform(get("/admin/notes")
                        .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteNote() throws Exception {
        mockMvc.perform(delete("/admin/notes/1")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(noteRepository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteNoteForUser() throws Exception {
        mockMvc.perform(delete("/admin/notes/1")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}