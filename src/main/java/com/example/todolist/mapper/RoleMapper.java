package com.example.todolist.mapper;

import com.example.todolist.dto.RoleDto;
import com.example.todolist.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role map(RoleDto role);
}
