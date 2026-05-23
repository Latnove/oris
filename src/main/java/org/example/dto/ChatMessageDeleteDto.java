package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDeleteDto {
    private Long id;

    public ChatMessageDeleteDto() {
    }

    public ChatMessageDeleteDto(Long id) {
        this.id = id;
    }
}
