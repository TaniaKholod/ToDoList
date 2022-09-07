package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.dto.request.UserRequestDTO;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTransformer {

    @Autowired
    static RoleService roleService;

    public static UserResponse userToUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName());
    }
    public static User userRequestDtoToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setPassword(userRequestDTO.getPassword());
        user.setEmail(userRequestDTO.getEmail());
        user.setRole(roleService.readById(2L));
        return user;
    }
}
