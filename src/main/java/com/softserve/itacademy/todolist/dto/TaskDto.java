package com.softserve.itacademy.todolist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.model.ToDo;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskDto {
    long id;

    @NotBlank(message = "The 'name' cannot be empty")
    String name;

    @NotNull
    String priority;

    @NotNull
    long todoId;

    ToDo toDo;

    @NotNull
    long stateId;

}
