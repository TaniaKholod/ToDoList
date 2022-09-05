package com.softserve.itacademy.todolist.mapper;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.dto.request.ToDoRequestDTO;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDTO;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-03T22:55:51+0300",
    comments = "version: 1.5.0.RC1, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDo toDoRequestToToDo(ToDoRequestDTO toDoRequestDTO) {
        if ( toDoRequestDTO == null ) {
            return null;
        }

        ToDo toDo = new ToDo();

        toDo.setOwner( toDoRequestDTOToUser( toDoRequestDTO ) );
        toDo.setId( toDoRequestDTO.getId() );
        toDo.setTitle( toDoRequestDTO.getTitle() );

        return toDo;
    }

    @Override
    public ToDoResponseDTO toDoToTodoResponse(ToDo toDo) {
        if ( toDo == null ) {
            return null;
        }

        ToDoResponseDTO toDoResponseDTO = new ToDoResponseDTO();

        Long id = toDoOwnerId( toDo );
        if ( id != null ) {
            toDoResponseDTO.setOwnerId( id );
        }
        toDoResponseDTO.setId( toDo.getId() );
        toDoResponseDTO.setTitle( toDo.getTitle() );
        toDoResponseDTO.setCreatedAt( toDo.getCreatedAt() );
        toDoResponseDTO.setTasks( taskListToTaskDtoList( toDo.getTasks() ) );

        return toDoResponseDTO;
    }

    protected User toDoRequestDTOToUser(ToDoRequestDTO toDoRequestDTO) {
        if ( toDoRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( toDoRequestDTO.getOwnerId() );

        return user;
    }

    private Long toDoOwnerId(ToDo toDo) {
        if ( toDo == null ) {
            return null;
        }
        User owner = toDo.getOwner();
        if ( owner == null ) {
            return null;
        }
        Long id = owner.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TaskDto taskToTaskDto(Task task) {
        if ( task == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String priority = null;

        if ( task.getId() != null ) {
            id = task.getId();
        }
        name = task.getName();
        if ( task.getPriority() != null ) {
            priority = task.getPriority().name();
        }

        long todoId = 0L;
        ToDo toDo = null;
        long stateId = 0L;

        TaskDto taskDto = new TaskDto( id, name, priority, todoId, toDo, stateId );

        return taskDto;
    }

    protected List<TaskDto> taskListToTaskDtoList(List<Task> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskDto> list1 = new ArrayList<TaskDto>( list.size() );
        for ( Task task : list ) {
            list1.add( taskToTaskDto( task ) );
        }

        return list1;
    }
}
