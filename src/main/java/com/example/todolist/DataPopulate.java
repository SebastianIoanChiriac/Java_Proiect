package com.example.todolist;

import com.example.todolist.dto.RoleDto;
import com.example.todolist.enums.ERole;
import com.example.todolist.repository.RoleRepository;
import com.example.todolist.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Transactional
@Component
public class DataPopulate implements CommandLineRunner {
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {
            Arrays.stream(ERole.values()).forEach(eRole -> {
                RoleDto role = new RoleDto();
                role.setName(eRole);
                roleService.save(role);
            });
        }


    }
}
