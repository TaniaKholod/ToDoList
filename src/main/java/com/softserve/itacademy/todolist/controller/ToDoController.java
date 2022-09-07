package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TodoDto;
import com.softserve.itacademy.todolist.dto.TodoTransformer;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{user-id}/todos")
public class ToDoController {

    @Autowired
    ToDoService toDoService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAll(@PathVariable("user-id") long userId, Principal principal) {
        checkAccess(principal, userId, null);
        return ResponseEntity.ok(toDoService.getByUserId(userId)
                .stream()
                .map(TodoTransformer::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<TodoDto> create(@PathVariable("user-id") long userId, @RequestBody TodoDto todoDto, Principal principal) {
        checkAccess(principal, userId, null);
        ToDo todo = TodoTransformer.convertToEntity(todoDto);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setOwner(userService.readById(userId));
        todo = toDoService.create(todo);
        return ResponseEntity.ok(TodoTransformer.convertToDto(todo));
    }

    @GetMapping("/{todo_id}")
    public ResponseEntity<TodoDto> read(@PathVariable("user-id") long userId, @PathVariable("todo-id") long todoId, Principal principal) {
        checkAccess(principal, userId, todoId);
        return ResponseEntity.ok(TodoTransformer.convertToDto(toDoService.readById(todoId)));
    }

    @PutMapping("/{todo_id}")
    public ResponseEntity<TodoDto> update(@PathVariable("user-id") long userId, @PathVariable("todo-id") long todoId,
                                  @RequestBody TodoDto todoDto, Principal principal) {
        checkAccess(principal, userId, todoId);
        ToDo todo = toDoService.update(TodoTransformer.convertToEntity(todoDto));
        return ResponseEntity.ok(TodoTransformer.convertToDto(todo));
    }


    @DeleteMapping("/{todo_id}")
    public ResponseEntity<String> delete(@PathVariable("user-id") long userId, @PathVariable("todo-id") long todoId, Principal principal) {
        checkAccess(principal, userId, todoId);
        toDoService.delete(todoId);
        return ResponseEntity.ok("ToDo with id " + todoId + " delete!");
    }

    private void checkAccess(Principal principal, Long userId, Long todoId) {
        User user = userService.findByEmail(principal.getName());
        if (user.getRole().getName().equals("ADMIN"))
            return;
        if (!user.equals(userService.readById(userId)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        if (todoId == null)
            return;
        ToDo todo = toDoService.readById(todoId);
        if (todo == null || todo.getOwner().getId() != userId)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
