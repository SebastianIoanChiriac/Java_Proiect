package com.example.todolist.service;

import com.example.todolist.dto.RoleDto;
import com.example.todolist.entity.Role;
import com.example.todolist.enums.ERole;

public interface RoleService {

    Role findByName(ERole name);

    Role save(RoleDto roleDto);

}
