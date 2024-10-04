package org.example.expert.domain.todo.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoSearchRequest {
    private String weather;
    private LocalDate start;
    private LocalDate end;
}
