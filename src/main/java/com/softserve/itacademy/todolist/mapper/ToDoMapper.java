package com.softserve.itacademy.todolist.mapper;

import com.softserve.itacademy.todolist.dto.request.ToDoRequestDTO;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDTO;
import com.softserve.itacademy.todolist.model.ToDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
    @Mapping(target = "owner.id", source = "toDoRequestDTO.ownerId")
    ToDo toDoRequestToToDo(ToDoRequestDTO toDoRequestDTO);
    @Mapping(target = "ownerId", source = "toDo.owner.id")
    ToDoResponseDTO toDoToTodoResponse(ToDo toDo);
}
