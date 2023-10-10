package com.example.todolist.service.impl;

import com.example.todolist.dto.RoleDto;
import com.example.todolist.entity.Role;
import com.example.todolist.enums.ERole;
import com.example.todolist.mapper.RoleMapper;
import com.example.todolist.repository.RoleRepository;
import com.example.todolist.service.RoleService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role findByName(ERole name) {
        return roleRepository.findByName(String.valueOf(name)).orElseThrow(() -> new ValidationException("Role with name %s doesn't exist".formatted(name)));
    }

    @Override
    public Role save(RoleDto roleDto) {
        return roleRepository.save(roleMapper.map(roleDto));
    }


}
