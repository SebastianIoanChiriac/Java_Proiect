package com.example.todolist.repository;

import com.example.todolist.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

    List<ToDoItem> findByToDoListId(Long id);
}
