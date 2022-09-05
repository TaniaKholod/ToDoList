package com.softserve.itacademy.todolist.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ToDoRequestDTO {
    private Long id;
    @NotBlank(message = "The 'title' cannot be empty")
    private String title;
    private long ownerId;
}
