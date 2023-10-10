package com.example.todolist.repository;

import com.example.todolist.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

    Optional<ToDoList> findByUserId(Long id);

}
