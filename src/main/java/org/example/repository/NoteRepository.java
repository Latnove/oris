package org.example.repository;

import org.example.model.Note;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    public List<Note> findByAuthor(User author);

    public List<Note> findByIsPublicTrue();

    @Query("SELECT n FROM Note n WHERE n.author = :author AND n.isPublic = true")
    List<Note> findPublicNotesByAuthor(@Param("author") User author);

}
