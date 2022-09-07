package com.softserve.itacademy.todolist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.User;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TodoDto {

    Long id;

    @NotBlank(message = "The 'title' cannot be empty")
    String title;

    @NotNull
    long ownerId;

    UserResponse owner;

}
