package org.example.todolist.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private UUID id;
    private String title;
    private String content;
    private LocalDateTime lastUpdatedOn;
}
