package com.softserve.itacademy.todolist.dto.response;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ToDoResponseDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private long ownerId;
    private List<TaskDto> tasks;
}
