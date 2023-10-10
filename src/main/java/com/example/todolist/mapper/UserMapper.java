package com.example.todolist.mapper;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserDto userDto);
    UserDto map(User user);
}
