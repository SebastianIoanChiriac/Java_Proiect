package com.example.todolist.controller;


import com.example.todolist.entity.ToDoItem;
import com.example.todolist.entity.ToDoItemsToDelete;
import com.example.todolist.entity.ToDoItemsToUpdate;
import com.example.todolist.entity.User;
import com.example.todolist.service.impl.ToDoItemServiceImpl;
import com.example.todolist.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ToDoController {
    private final UserServiceImpl userService;
    private final ToDoItemServiceImpl toDoItemService;

    @GetMapping("/todo")
    public String toDoOfLoggedInUser(Model model) {
        User user = userService.getCurrentUser();
        String name = user.getFirstName() + " " + user.getLastName();
        model.addAttribute("welcomeMessage", "Hello, " + name + "!");

        ToDoItem toDoItem = new ToDoItem();
        model.addAttribute("toDoItem", toDoItem);
        List<ToDoItem> toDoItemList = toDoItemService.getToDoItemsOfCurrentUser();
        model.addAttribute("emptyToDoList", toDoItemList.isEmpty());
        model.addAttribute("toDoItemList", toDoItemList);

        ToDoItemsToDelete toDoItemsToDelete = new ToDoItemsToDelete();
        toDoItemsToDelete.setList(new Long[0]);
        model.addAttribute("toDoItemsToDelete", toDoItemsToDelete);

        ToDoItemsToUpdate toDoItemsToUpdate = new ToDoItemsToUpdate();
        List<String> list=new ArrayList<>();
        toDoItemsToUpdate.setList(list);
        model.addAttribute("toDoItemsToUpdate", toDoItemsToUpdate);

        return "todo";
    }

    @PostMapping("/todo/add-todo-item")
    public String addToDoItemToCurrentUserList(@ModelAttribute("toDoItem") ToDoItem toDoItem, Model model) {
        toDoItemService.addToDoItemToCurrentUserToDoList(toDoItem);
        List<ToDoItem> toDoItemList = toDoItemService.getToDoItemsOfCurrentUser();
        model.addAttribute("toDoItemList", toDoItemList);
        model.addAttribute("emptyToDoList", toDoItemList.isEmpty());
        return "redirect:/todo?success";

    }

    //delete
    @GetMapping("/todo/todoItem")
    public String deleteToDoItem(Model model, @ModelAttribute("toDoItemsToDelete") ToDoItemsToDelete toDoItemsToDelete) {
        toDoItemService.deleteToDoItems(toDoItemsToDelete.getList());

        List<ToDoItem> toDoItemList = toDoItemService.getToDoItemsOfCurrentUser();
        model.addAttribute("toDoItemList", toDoItemList);
        model.addAttribute("emptyToDoList", toDoItemList.isEmpty());
        return "redirect:/todo?successDelete";
    }
    //update
    @GetMapping("/todo/todoItemUpdate")
    public String updateToDoItem(Model model, @ModelAttribute("toDoItemsToUpdate") ToDoItemsToUpdate toDoItemsToUpdate) {

        toDoItemService.updateToDoItems(toDoItemsToUpdate.getList());

        List<ToDoItem> toDoItemList = toDoItemService.getToDoItemsOfCurrentUser();
        model.addAttribute("toDoItemList", toDoItemList);
        model.addAttribute("emptyToDoList", toDoItemList.isEmpty());
        return "redirect:/todo?successUpdate";
    }
}
