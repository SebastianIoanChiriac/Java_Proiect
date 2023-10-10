package com.example.todolist.service;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.Role;
import com.example.todolist.entity.User;
import com.example.todolist.enums.ERole;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.repository.RoleRepository;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.impl.UserServiceImpl;
import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    SecurityContextImpl securityContext;

    @Mock
    Authentication authentication;

    @Test
    public void testSaveUserSuccessful() {
        UserDto userDto = new UserDto();
        userDto.setPassword("secretpassword");
        userDto.setEmail("sebastian@gmail.com");
        userDto.setFirstName("Sebastian");
        userDto.setLastName("Test");

        Role role = new Role();
        role.setId(1L);
        role.setName(String.valueOf(ERole.ROLE_USER));

        when(passwordEncoder.encode("secretpassword")).thenReturn("passwordencoded");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        User user = new User();
        user.setId(1L);
        user.setEmail("sebastian@gmail.com");
        user.setFirstName("Sebastian");
        user.setLastName("Test");
        user.setPassword("passwordencoded");
        user.setRoles(List.of(role));

        when(userMapper.map(userDto)).thenReturn(user);
        lenient().when(userRepository.save(any())).thenReturn(user);
        User userSaved = userService.saveUser(userDto);
        Assertions.assertThat(userSaved.getPassword()).isEqualTo("passwordencoded");
        Assertions.assertThat(userSaved.getEmail()).isEqualTo("sebastian@gmail.com");

    }

    @Test
    public void testSaveUserUnsuccessfull() {
        UserDto userDto = new UserDto();
        userDto.setPassword("secretpassword");
        userDto.setEmail("sebastian@gmail.com");
        userDto.setFirstName("Sebastian");
        userDto.setLastName("Test");

        Role role = new Role();
        role.setId(1L);
        role.setName(String.valueOf(ERole.ROLE_USER));

        when(passwordEncoder.encode("secretpassword")).thenReturn("passwordencoded");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        User user = new User();
        user.setId(1L);
        user.setEmail("sebastian@gmail.com");
        user.setFirstName("Sebastian");
        user.setLastName("Test");
        user.setPassword("passwordencoded");
        user.setRoles(List.of(role));

        when(userMapper.map(userDto)).thenReturn(user);
        lenient().when(userRepository.save(any())).thenThrow(RuntimeException.class);
        AssertionsForClassTypes.assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> userService.saveUser(userDto))
                .withMessage("User with email: sebastian@gmail.com already exists.");
    }


    @Test
    void testFindByEmailSuccessful() {
        User user = new User();
        user.setEmail("email");
        user.setFirstName("Marian");
        when(userRepository.findByEmail("email")).thenReturn(user);
        Assertions.assertThat(userService.findByEmail("email").getFirstName()).isEqualTo("Marian");
    }

    @Test
    void testGetCurrentUser() {
        User user = new User();
        user.setEmail("email");
        user.setId(3L);
        when(authentication.getName()).thenReturn("Sebastian");
        Mockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByEmail("Sebastian")).thenReturn(user);
        Assertions.assertThat(userService.getCurrentUser().getEmail()).isEqualTo("email");
        Assertions.assertThat(userService.getCurrentUserId()).isEqualTo(3L);


    }

}
