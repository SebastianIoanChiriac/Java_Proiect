package com.example.todolist.service;

import com.example.todolist.entity.ToDoList;
import com.example.todolist.entity.User;
import com.example.todolist.repository.ToDoListRepository;
import com.example.todolist.service.impl.ToDoListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceImplTest {

    @Mock
    public  ToDoListRepository toDoListRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ToDoListServiceImpl toDoListService;

    @Test
    void testCreateCurrentUserToDoList(){
        User user=new User();
        user.setId(2L);
        ToDoList toDoList=new ToDoList();
        toDoList.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        when(toDoListRepository.save(any())).thenReturn(toDoList);
        assertThat(toDoListService.createCurrentUserToDoList().getUser().getId()).isEqualTo(2L);

    }

    @Test
    void testGetToDoListOfCurrentUser(){
        when(userService.getCurrentUserId()).thenReturn(1L);
        when(toDoListRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThat(toDoListService.getToDoListOfCurrentUser()).isEqualTo(Optional.empty());
    }

}
