package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.model.ToDo;

public class TodoTransformer {

    public static TodoDto convertToDto(ToDo todo) {
        return new TodoDto(todo.getId(),
                todo.getTitle(),
                todo.getOwner().getId(),
                UserTransformer.userToUserResponse(todo.getOwner()));
    }

    public static ToDo convertToEntity(TodoDto todoDto) {
        ToDo todo = new ToDo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        return todo;
    }
}
