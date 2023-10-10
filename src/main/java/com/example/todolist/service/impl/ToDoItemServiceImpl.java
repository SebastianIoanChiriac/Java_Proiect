package com.example.todolist.service.impl;


import com.example.todolist.entity.ToDoItem;
import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoItemRepository;
import com.example.todolist.service.ToDoItemService;
import com.example.todolist.service.UserService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ToDoItemServiceImpl implements ToDoItemService {

    public final ToDoItemRepository toDoItemRepository;
    public final UserService userService;
    public final ToDoListServiceImpl toDoListService;

    @Override
    public ToDoItem addToDoItemToCurrentUserToDoList(ToDoItem toDoItem) {
        Optional<ToDoList> toDoListOptional = toDoListService.getToDoListOfCurrentUser();

        if (toDoListOptional.isPresent()) {
            toDoItem.setToDoList(toDoListOptional.get());
        } else {
            ToDoList toDoList = toDoListService.createCurrentUserToDoList();
            toDoItem.setToDoList(toDoList);
        }
        return toDoItemRepository.save(toDoItem);

    }

    @Override
    public List<ToDoItem> getToDoItemsOfCurrentUser() {
        List<ToDoItem> toDoItemList = new ArrayList<>();
        Optional<ToDoList> toDoListOptional = toDoListService.getToDoListOfCurrentUser();
        if (toDoListOptional.isPresent()) {
            toDoItemList = toDoItemRepository.findByToDoListId(toDoListOptional.get().getId());
        }
        return toDoItemList;

    }


    @Override
    public void deleteToDoItems(Long[] ids) {
        toDoItemRepository.findAllById(List.of(ids)).forEach(toDoItem -> toDoItemRepository.deleteById(toDoItem.getId()));
    }

    @Override
    @Transactional
    public List<ToDoItem> updateToDoItems(List<String> list) {
        for (int i = 0; i < list.size(); i += 2) {
            Long id = Long.valueOf(list.get(i));
            String newValue = list.get(i + 1);
            ToDoItem toDoItem = toDoItemRepository.findById(id).orElseThrow(() -> new ValidationException("Item with id %s doesn't exist".formatted(id)));
            toDoItem.setItem(newValue);

        }
        return getToDoItemsOfCurrentUser();
    }
}
