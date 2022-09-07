package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.UserTransformer;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.security.SecurityUser;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{user-id}/todos/{todo-id}/collaborators")
public class CollaboratorController {

    @Autowired
    ToDoService todoService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(@PathVariable("user-id") Long userId, @PathVariable("todo-id") Long todoId, Principal principal) {
        checkAccess(principal, userId, todoId);
        ToDo todo = todoService.readById(todoId);
        return ResponseEntity.ok(todo.getCollaborators().stream()
                .map(UserTransformer::userToUserResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<List<UserResponse>> addCollaborator(
            @PathVariable("user-id") Long userId,
            @PathVariable("todo-id") Long todoId,
            @Validated @RequestParam(value = "collaborator-id", required = true) Long collaboratorId,
            Principal principal) {
        checkAccess(principal, userId, todoId);
        ToDo toDo = todoService.readById(todoId);
        List<User> collaborators = toDo.getCollaborators();
        User collaborator = userService.readById(collaboratorId);
        collaborators.add(collaborator);
        todoService.update(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(location).body(collaborators.stream()
                .map(UserTransformer::userToUserResponse)
                .collect(Collectors.toList()));
    }

    @DeleteMapping
    public ResponseEntity<List<UserResponse>> deleteCollaborator(
            @PathVariable("user-id") Long userId,
            @PathVariable("todo-id") Long todoId,
            @Validated @RequestParam(value = "collaborator-id", required = true) Long collaboratorId,
            Principal principal) {
        checkAccess(principal, userId, todoId);
        ToDo toDo = todoService.readById(todoId);
        List<User> collaborators = toDo.getCollaborators();
        collaborators.remove(userService.readById(collaboratorId));
        toDo.setCollaborators(collaborators);
        todoService.update(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(location).body(collaborators.stream()
                .map(UserTransformer::userToUserResponse)
                .collect(Collectors.toList()));
    }

    private void checkAccess(Principal principal, Long userId, Long todoId) {
        User user = userService.findByEmail(principal.getName());
        if (user.getRole().getName().equals("ADMIN"))
            return;
        if (!user.equals(userService.readById(userId)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        ToDo todo = todoService.readById(todoId);
        if (todo == null || todo.getOwner().getId() != userId)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}



