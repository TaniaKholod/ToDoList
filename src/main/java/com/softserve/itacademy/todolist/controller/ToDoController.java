package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.request.ToDoRequestDTO;
import com.softserve.itacademy.todolist.dto.response.ToDoResponseDTO;
import com.softserve.itacademy.todolist.mapper.ToDoMapper;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{user_id}/todos")
public class ToDoController {
    @Autowired
    ToDoMapper toDoMapper;
    @Autowired
    ToDoService toDoService;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<ToDoResponseDTO>> getAll(@PathVariable("user_id") long id) {
        return ResponseEntity.ok(toDoService.getByUserId(id)
                .stream()
                .map(toDo -> toDoMapper.toDoToTodoResponse(toDo))
                .collect(Collectors.toList()));
//        return toDoService.getAll()
//                .stream()
//                .map(ToDoResponseDTO::new)
//                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<ToDoResponseDTO> create(@PathVariable("user_id") long id, @RequestBody ToDoRequestDTO toDoRequestDTO) {
        ToDo newToDo = toDoMapper.toDoRequestToToDo(toDoRequestDTO);
        newToDo.setCreatedAt(LocalDateTime.now());
        newToDo.setOwner(userService.readById(id));
        ToDo todo = toDoService.create(newToDo);
        return ResponseEntity.ok(toDoMapper.toDoToTodoResponse(todo));
    }

    @GetMapping("/{todo_id}")
    public ResponseEntity<ToDoResponseDTO> read(@PathVariable("todo_id") long todoId) {
        return ResponseEntity.ok(toDoMapper.toDoToTodoResponse(toDoService.readById(todoId)));
    }

    @PutMapping("/{todo_id}/update")
    public ResponseEntity<ToDoResponseDTO> update(@PathVariable("user_id") long id, @PathVariable("todo_id") long todoId,
                                  @RequestBody ToDoRequestDTO toDoRequestDTO) {
        ToDo todo = toDoService.update(toDoMapper.toDoRequestToToDo(toDoRequestDTO));
        return ResponseEntity.ok(toDoMapper.toDoToTodoResponse(todo));
    }


    @DeleteMapping("/{todo_id}/delete/")
    public ResponseEntity<String> delete(@PathVariable("todo_id") long todoId, @PathVariable("user_id") long ownerId) {
        toDoService.delete(todoId);
        return ResponseEntity.ok("ToDo with id " + todoId + " delete!");
    }
}
