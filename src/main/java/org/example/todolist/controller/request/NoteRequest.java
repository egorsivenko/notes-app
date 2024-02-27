package org.example.todolist.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    @NotBlank(message = "Title cannot be null, empty or contain only whitespaces")
    @Size(max = 100)
    private String title;

    @NotNull(message = "Content cannot be null")
    @Size(max = 500)
    private String content;
}
