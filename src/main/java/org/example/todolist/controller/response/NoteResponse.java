package org.example.todolist.controller.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteResponse(
        UUID id,
        String title,
        String content,
        LocalDateTime lastUpdatedOn
) {}
