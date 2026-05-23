package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChatMessageDeleteDto;
import org.example.dto.ChatMessageDto;
import org.example.model.ChatMessage;
import org.example.model.User;
import org.example.repository.ChatMessageRepository;
import org.example.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public String chat(Model model, Authentication authentication) {
        model.addAttribute("messages", recentMessagesOldestFirst());
        model.addAttribute("isAdmin", isAdmin(authentication));
        return "chat";
    }

    @GetMapping("/public")
    public String publicChat(Model model) {
        model.addAttribute("messages", recentMessagesOldestFirst());
        return "public_chat";
    }

    @GetMapping("/my")
    public String myMessages(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("messages", chatMessageRepository.findByAuthor(user).stream()
                .map(ChatMessageDto::from)
                .toList());
        return "my_messages";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        ChatMessage message = chatMessageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!message.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нельзя удалить чужое сообщение");
        }

        chatMessageRepository.delete(message);
        messagingTemplate.convertAndSend("/topic/message-deletions", new ChatMessageDeleteDto(id));
        return "redirect:/chat/my";
    }

    private List<ChatMessageDto> recentMessagesOldestFirst() {
        List<ChatMessage> messages = new ArrayList<>(chatMessageRepository.findTop50ByOrderBySentAtDesc());
        messages.sort((left, right) -> left.getSentAt().compareTo(right.getSentAt()));
        return messages.stream()
                .map(ChatMessageDto::from)
                .toList();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
