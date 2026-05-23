package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChatMessageDeleteDto;
import org.example.dto.ChatMessageDto;
import org.example.model.ChatMessage;
import org.example.repository.ChatMessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin/messages")
@RequiredArgsConstructor
public class AdminChatController {
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public List<ChatMessageDto> getAllMessages() {
        return chatMessageRepository.findAll().stream()
                .map(ChatMessageDto::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        ChatMessage message = chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        chatMessageRepository.delete(message);
        messagingTemplate.convertAndSend("/topic/message-deletions", new ChatMessageDeleteDto(id));
    }
}
