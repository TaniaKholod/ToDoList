package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.request.UserRequestDTO;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.mapper.UserMapper;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.RoleService;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}/read")
    public ResponseEntity<UserResponse> userRead(@PathVariable long id){
        return ResponseEntity.ok(userMapper.userToUserResponse(userService.readById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@Validated @RequestBody UserRequestDTO userRequestDTO) {
        User user = userMapper.userRequestToUser(userRequestDTO);
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<User> update(@PathVariable long id,@Validated @RequestBody UserRequestDTO userRequestDTO){
        User user = userMapper.userRequestToUser(userRequestDTO);
        user.setId(id);
       return ResponseEntity.ok(userService.update(user));

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok("User with id " + id + " delete!");
    }
}
