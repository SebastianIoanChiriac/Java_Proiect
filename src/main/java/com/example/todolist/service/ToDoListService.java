package com.example.todolist.service;

import com.example.todolist.entity.ToDoList;

import java.util.Optional;

public interface ToDoListService {
    ToDoList createCurrentUserToDoList();
    Optional<ToDoList> getToDoListOfCurrentUser();
}
