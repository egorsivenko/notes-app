package org.example.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private UUID id;
    private String title;
    private String content;
    private LocalDateTime lastUpdatedOn;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
