package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.ChatMessage;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;
    private String content;
    private LocalDateTime sentAt;
    private Long authorId;
    private String authorUsername;

    public ChatMessageDto() {
    }

    public ChatMessageDto(Long id, String content, LocalDateTime sentAt, Long authorId, String authorUsername) {
        this.id = id;
        this.content = content;
        this.sentAt = sentAt;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
    }

    public static ChatMessageDto from(ChatMessage message) {
        return new ChatMessageDto(
                message.getId(),
                message.getContent(),
                message.getSentAt(),
                message.getAuthor().getId(),
                message.getAuthor().getUsername()
        );
    }
}
