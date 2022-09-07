package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.UserTransformer;
import com.softserve.itacademy.todolist.dto.request.UserRequestDTO;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.security.SecurityUser;
import com.softserve.itacademy.todolist.service.RoleService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(Principal principal) {
        checkAccess(principal, null);
        return ResponseEntity.ok(userService.getAll().stream()
                .map(UserTransformer::userToUserResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> userRead(@PathVariable("user-id") long userId, Principal principal){
        checkAccess(principal, userId);
        return ResponseEntity.ok(UserTransformer.userToUserResponse(userService.readById(userId)));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequestDTO userRequestDTO) {
        User user = UserTransformer.userRequestDtoToUser(userRequestDTO);
        user = userService.create(user);
        return ResponseEntity.ok(UserTransformer.userToUserResponse(user));
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<UserResponse> update(@PathVariable("user-id") long userId, @Validated @RequestBody UserRequestDTO userRequestDTO, Principal principal){
        checkAccess(principal, null);
        User user = UserTransformer.userRequestDtoToUser(userRequestDTO);
        user.setId(userId);
       return ResponseEntity.ok(UserTransformer.userToUserResponse(user));

    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> delete(@PathVariable("user-id") long userId, Principal principal) {
        checkAccess(principal, null);
        userService.delete(userId);
        return ResponseEntity.ok("User with id " + userId + " delete!");
    }

    private void checkAccess(Principal principal, Long userId) {
        User user = userService.findByEmail(principal.getName());
        if (user.getRole().getName().equals("ADMIN"))
            return;
        if (userId == null || !user.equals(userService.readById(userId)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
