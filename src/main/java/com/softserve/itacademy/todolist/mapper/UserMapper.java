package com.softserve.itacademy.todolist.mapper;

import com.softserve.itacademy.todolist.dto.request.UserRequestDTO;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userRequestToUser(UserRequestDTO userRequestDTO);
    UserResponse userToUserResponse(User user);
}
