package com.example.todolist.service.impl;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.Role;
import com.example.todolist.entity.User;
import com.example.todolist.enums.ERole;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.repository.RoleRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(UserDto userDto) {
        String rawPassword = userDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userDto.setPassword(encodedPassword);
        Optional<Role> role = roleRepository.findByName(String.valueOf(ERole.ROLE_USER));
        User user = userMapper.map(userDto);
        role.ifPresent(value -> user.setRoles(List.of(value)));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ValidationException("User with email: %s already exists.".formatted(userDto.getEmail()));
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return findByEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }


    @Override
    public UserDto getCurrentUserDto() {
        return userMapper.map(getCurrentUser());
    }


}
