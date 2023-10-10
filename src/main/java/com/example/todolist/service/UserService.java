package com.example.todolist.service;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.User;

public interface UserService {
    User saveUser(UserDto userDto);

    User findByEmail(String email);

    User getCurrentUser();

    Long getCurrentUserId();

    UserDto getCurrentUserDto();
}
