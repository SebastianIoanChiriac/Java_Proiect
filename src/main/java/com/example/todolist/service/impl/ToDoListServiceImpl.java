package com.example.todolist.service.impl;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.entity.User;
import com.example.todolist.repository.ToDoListRepository;
import com.example.todolist.service.ToDoListService;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ToDoListServiceImpl implements ToDoListService {

    public final ToDoListRepository toDoListRepository;
    private final UserService userService;

    @Override
    public ToDoList createCurrentUserToDoList() {
        User user = userService.getCurrentUser();
        ToDoList toDoList = new ToDoList();
        toDoList.setUser(user);
        return toDoListRepository.save(toDoList);
    }

    @Override
    public Optional<ToDoList> getToDoListOfCurrentUser() {
        return toDoListRepository.findByUserId(userService.getCurrentUserId());
    }


}
