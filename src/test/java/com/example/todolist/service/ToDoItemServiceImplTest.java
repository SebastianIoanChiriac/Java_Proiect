package com.example.todolist.service;

import com.example.todolist.entity.ToDoItem;
import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoItemRepository;
import com.example.todolist.service.impl.ToDoItemServiceImpl;
import com.example.todolist.service.impl.ToDoListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoItemServiceImplTest {

    @Mock
    public ToDoItemRepository toDoItemRepository;
    @Mock
    public UserService userService;
    @Mock
    public ToDoListServiceImpl toDoListService;

    @InjectMocks
    public ToDoItemServiceImpl toDoItemService;

    @Test
    void testAddToDoItemToCurrentUserToDoList() {
        ToDoList toDoList = new ToDoList();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setItem("going to shopping");
        when(toDoListService.getToDoListOfCurrentUser()).thenReturn(Optional.of(toDoList));
        when(toDoItemRepository.save(any())).thenReturn(toDoItem);
        assertThat(toDoItemService.addToDoItemToCurrentUserToDoList(toDoItem).getItem()).isEqualTo("going to shopping");

    }

    @Test
    void testAddToDoItemToNotExistingUserToDoList() {
        ToDoList toDoList = new ToDoList();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setItem("going to shopping");
        when(toDoListService.getToDoListOfCurrentUser()).thenReturn(Optional.empty());
        when(toDoListService.createCurrentUserToDoList()).thenReturn(toDoList);
        when(toDoItemRepository.save(any())).thenReturn(toDoItem);
        assertThat(toDoItemService.addToDoItemToCurrentUserToDoList(toDoItem).getItem()).isEqualTo("going to shopping");

    }

    @Test
    void testGetToDoItemsOfCurrentUser_notExistingList() {
        when(toDoListService.getToDoListOfCurrentUser()).thenReturn(Optional.empty());
        assertThat(toDoItemService.getToDoItemsOfCurrentUser().size()).isZero();
    }

    @Test
    void testGetToDoItemsOfCurrentUser_existingList() {
        ToDoList toDoList = new ToDoList();
        toDoList.setId(1L);
        when(toDoListService.getToDoListOfCurrentUser()).thenReturn(Optional.of(toDoList));
        when(toDoItemRepository.findByToDoListId(1L)).thenReturn(List.of());
        assertThat(toDoItemService.getToDoItemsOfCurrentUser().size()).isZero();
    }

    @Test
    void testUpdateToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(1L);
        toDoItem.setItem("first item");
        List<String> list1=new ArrayList<>();
        list1.add("1");
        list1.add("first item");
        when(toDoItemRepository.findById(1L)).thenReturn(Optional.of(toDoItem));

        List<ToDoItem> list=new ArrayList<>();

        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setId(1L);
        toDoItem1.setItem("first item has new value");

        list.add(toDoItem1);

        ToDoList toDoList = new ToDoList();
        toDoList.setId(1L);

        when(toDoListService.getToDoListOfCurrentUser()).thenReturn(Optional.of(toDoList));
        when(toDoItemRepository.findByToDoListId(1L)).thenReturn(List.of(toDoItem1));

        assertThat(toDoItemService.updateToDoItems(list1)).isEqualTo(list);
    }


}
