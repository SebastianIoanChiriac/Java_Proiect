package com.example.todolist.controller;

import com.example.todolist.config.SpringSecurity;
import com.example.todolist.entity.ToDoItem;
import com.example.todolist.entity.User;
import com.example.todolist.service.impl.ToDoItemServiceImpl;
import com.example.todolist.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@Import(SpringSecurity.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ToDoItemServiceImpl toDoItemService;

    @Test
    @WithMockUser(username = "test", password = "passwordsecret", authorities = {"ROLE_USER"})
    void toDoOfLoggedInUser() throws Exception {

        User user = new User();
        user.setLastName("Test");
        user.setFirstName("Sebastian");
        when(userService.getCurrentUser()).thenReturn(user);
        when(toDoItemService.getToDoItemsOfCurrentUser()).thenReturn(List.of());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/todo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("welcomeMessage", "Hello, Sebastian Test!"))
                .andExpect(MockMvcResultMatchers.model().attribute("toDoItem", new ToDoItem()))
                .andExpect(MockMvcResultMatchers.model().attribute("emptyToDoList", true))
                .andExpect(MockMvcResultMatchers.model().attribute("toDoItemList", List.of()))
                .andExpect(MockMvcResultMatchers.view().name("todo"));

    }


}
