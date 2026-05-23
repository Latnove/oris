package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChatMessageDto;
import org.example.dto.ChatMessageRequest;
import org.example.model.ChatMessage;
import org.example.model.User;
import org.example.repository.ChatMessageRepository;
import org.example.service.impl.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatMessageHandler {
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageDto send(@Payload ChatMessageRequest request, Principal principal) {
        if (principal == null) {
            throw new AccessDeniedException("Нужно войти в аккаунт");
        }

        String content = request == null || request.getContent() == null ? "" : request.getContent().trim();
        if (content.isBlank()) {
            throw new IllegalArgumentException("Сообщение не может быть пустым");
        }

        User author = userService.findByUsername(principal.getName());

        ChatMessage message = new ChatMessage();
        message.setContent(content);
        message.setAuthor(author);

        return ChatMessageDto.from(chatMessageRepository.save(message));
    }
}
