package com.example.todolist.service;

import com.example.todolist.entity.ToDoItem;

import java.util.List;

public interface ToDoItemService {
    List<ToDoItem> updateToDoItems(List<String> list);

    void deleteToDoItems(Long[] id);

    List<ToDoItem> getToDoItemsOfCurrentUser();

    ToDoItem addToDoItemToCurrentUserToDoList(ToDoItem toDoItem);


}
