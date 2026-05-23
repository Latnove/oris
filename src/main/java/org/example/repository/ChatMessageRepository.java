package org.example.repository;

import org.example.model.ChatMessage;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop50ByOrderBySentAtDesc();

    List<ChatMessage> findByAuthor(User author);

    @Query("""
            select message from ChatMessage message
            where lower(message.content) like lower(concat('%', :content, '%'))
            order by message.sentAt desc
            """)
    List<ChatMessage> searchByContent(@Param("content") String content);
}
