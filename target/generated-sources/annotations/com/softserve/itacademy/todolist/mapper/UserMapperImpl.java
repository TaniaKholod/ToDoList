package com.softserve.itacademy.todolist.mapper;

import com.softserve.itacademy.todolist.dto.request.UserRequestDTO;
import com.softserve.itacademy.todolist.dto.response.UserResponse;
import com.softserve.itacademy.todolist.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-03T22:55:51+0300",
    comments = "version: 1.5.0.RC1, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userRequestToUser(UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( userRequestDTO.getFirstName() );
        user.setLastName( userRequestDTO.getLastName() );
        user.setEmail( userRequestDTO.getEmail() );
        user.setPassword( userRequestDTO.getPassword() );
        user.setRole( userRequestDTO.getRole() );

        return user;
    }

    @Override
    public UserResponse userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        User user1 = null;

        user1 = user;

        UserResponse userResponse = new UserResponse( user1 );

        return userResponse;
    }
}
