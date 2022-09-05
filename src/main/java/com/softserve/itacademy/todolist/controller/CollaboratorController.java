package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.dto.TaskTransformer;
import com.softserve.itacademy.todolist.dto.response.CollaboratorResponse;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{u_id}/todos/{t_id}/collaborators")
public class CollaboratorController {

    @Autowired
    ToDoService todoService;

    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<List<CollaboratorResponse>> getAll(@PathVariable("u_id") Long user_id, @PathVariable("t_id") Long todo_id, Principal principal) {
        ToDo todo = todoService.readById(todo_id);
        return ResponseEntity.ok(todo.getCollaborators().stream()
                .map(CollaboratorResponse::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCollaborator(
            @PathVariable("u_id") Long u_id,
            @PathVariable("t_id") Long t_id,
            @Validated @RequestParam(value = "user_id", required = true) Long userId,
            Principal principal) {
        ToDo toDo = todoService.readById(t_id);
        List<User> collaborators = toDo.getCollaborators();
        collaborators.add(userService.readById(userId));
        todoService.update(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{u_id}/todos/{t_id}/collaborators")
                .buildAndExpand(toDo.getOwner().getId(), toDo.getId())
                .toUri();
        return ResponseEntity.created(location).body(collaborators.stream()
                .map(CollaboratorResponse::new)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCollaborator(
            @PathVariable("u_id") Long u_id,
            @PathVariable("t_id") Long t_id,
            @Validated @RequestParam(value = "user_id", required = true) Long userId,
            Principal principal) {
        ToDo toDo = todoService.readById(t_id);
        List<User> collaborators = toDo.getCollaborators();
        collaborators.remove(userService.readById(userId));
        toDo.setCollaborators(collaborators);
        todoService.update(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{u_id}/todos/{t_id}/collaborators")
                .buildAndExpand(u_id, toDo.getId())
                .toUri();
        return ResponseEntity.created(location).body(collaborators.stream()
                .map(CollaboratorResponse::new)
                .collect(Collectors.toList()));
    }
}



