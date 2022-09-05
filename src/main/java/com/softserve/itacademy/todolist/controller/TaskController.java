package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TaskDto;
import com.softserve.itacademy.todolist.dto.TaskTransformer;
import com.softserve.itacademy.todolist.model.*;
import com.softserve.itacademy.todolist.service.StateService;
import com.softserve.itacademy.todolist.service.TaskService;
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
@RequestMapping("/api/users/{user-id}/todos/{todo-id}/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    ToDoService toDoService;
    @Autowired
    StateService stateService;
    @Autowired
    UserService userService;

    @GetMapping()
    //@PreAuthorize("hasAuthority('ADMIN') or " +
    //        "hasAuthority('USER') and authentication.principal.getName().equals(@userServiceImpl.readById(#user-id).password)")
    public List<TaskDto> getAll(@PathVariable("todo-id") long todoId, @PathVariable("user-id") Long userId, Principal principal) {
        checkAccess(principal, userId, todoId);
        return taskService.getByTodoId(todoId)
                .stream()
                .map(TaskTransformer::convertToDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/{task-id}")
    //@PreAuthorize("hasAuthority('ADMIN') or " +
    //        "hasAuthority('USER') and authentication.details.id == #user-id and " +
    //        "authentication.details.id == @toDoServiceImpl.readById(#todo-id).owner.id and" +
    //        "@taskServiceImpl.readById(#task-id).todo.id == #todo-id")
    public TaskDto getTask(@PathVariable("task-id") long taskId, @PathVariable("user-id") long userId, @PathVariable("todo-id") long todoId, Principal principal) {
        checkAccess(principal, userId, todoId);
        return TaskTransformer.convertToDto(taskService.readById(taskId));

    }

    @DeleteMapping("/{task-id}")
    //@PreAuthorize("hasAuthority('ADMIN') or " +
    //        "hasAuthority('USER') and authentication.details.id == #user-id and " +
    //        "authentication.details.id == @toDoServiceImpl.readById(#todo-id).owner.id and" +
    //        "@taskServiceImpl.readById(#task-id).todo.id == #todo-id")
    public void deleteTask(@PathVariable("task-id") long taskId, @PathVariable("user-id") Long userId, @PathVariable("todo-id") long todoId, Principal principal) {
        checkAccess(principal, userId, todoId);
        taskService.delete(taskId);

    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN') or " +
    //        "hasAuthority('USER') and authentication.details.id == #user-id and " +
    //        "authentication.details.id == @toDoServiceImpl.readById(#todo-id).owner.id")
    public ResponseEntity<?> createTask (@Validated @RequestBody TaskDto taskDto, @PathVariable("todo-id") long todoId,
                                         @PathVariable("user-id") Long userId, Principal principal) {
        checkAccess(principal, userId, todoId);
        Task task = TaskTransformer.convertToEntity(taskDto, toDoService.readById(todoId), stateService.readById(taskDto.getStateId()));
        task = taskService.create(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{taskId}")
                .buildAndExpand(task.getId())
                .toUri();
        return ResponseEntity.created(location).body(TaskTransformer.convertToDto(task));
    }

    @PutMapping
    //@PreAuthorize("hasAuthority('ADMIN') or " +
    //        "hasAuthority('USER') and authentication.details.id == #user-id and " +
    //        "authentication.details.id == @toDoServiceImpl.readById(#todo-id).owner.id")
    public ResponseEntity<?> updateTask (@Validated @RequestBody TaskDto taskDto, @PathVariable("todo-id") long todoId,
                                         @PathVariable("user-id") Long userId, Principal principal) {
        checkAccess(principal, userId, todoId);
        Task task = taskService.readById(taskDto.getId());
        if (task == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found.");
        if (!taskDto.getPriority().equals(task.getPriority().name()))
            task.setPriority(Priority.valueOf(taskDto.getPriority()));
        if (!taskDto.getName().equals(task.getName()))
            task.setName(taskDto.getName());
        if (taskDto.getStateId() != task.getId())
            task.setTodo(toDoService.readById(taskDto.getTodoId()));
        if (taskDto.getStateId() != task.getState().getId())
            task.setState(stateService.readById(taskDto.getStateId()));
        task = taskService.update(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{taskId}")
                .buildAndExpand(task.getId())
                .toUri();
        return ResponseEntity.created(location).body(TaskTransformer.convertToDto(task));
    }

    private void checkAccess(Principal principal, long userId, long todoId) {
        User user = userService.findByEmail(principal.getName());
        if (user.getRole().getName().equals("ADMIN"))
            return;
        if (!userService.findByEmail(principal.getName()).equals(userService.readById(userId)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        ToDo todo = toDoService.readById(todoId);
        if (todo == null || todo.getOwner().getId() != userId)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
