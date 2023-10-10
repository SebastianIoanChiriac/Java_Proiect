package com.example.todolist.service;

import com.example.todolist.dto.RoleDto;
import com.example.todolist.entity.Role;
import com.example.todolist.enums.ERole;
import com.example.todolist.exception.EntityNotFoundException;
import com.example.todolist.mapper.RoleMapper;
import com.example.todolist.repository.RoleRepository;
import com.example.todolist.service.impl.RoleServiceImpl;
import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private  RoleRepository roleRepository;
    @Mock
    private  RoleMapper roleMapper;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void findByNameSuccessful(){
        Role role=new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        Assertions.assertThat(roleService.findByName(any()).getName()).isEqualTo(String.valueOf(ERole.ROLE_USER));
    }

    @Test
    void findByNameUnsuccessful(){
        when(roleRepository.findByName(anyString())).thenThrow(new ValidationException("Role with name ROLE_USER doesn't exist"));
        AssertionsForClassTypes.assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> roleService.findByName(any()))
                .withMessage("Role with name ROLE_USER doesn't exist");
    }


}
