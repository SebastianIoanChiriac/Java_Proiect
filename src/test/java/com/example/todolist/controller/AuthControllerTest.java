package com.example.todolist.controller;

import com.example.todolist.config.SpringSecurity;
import com.example.todolist.entity.User;
import com.example.todolist.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(SpringSecurity.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void shouldAllowAccessForAnonymousUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    void shouldAllowAccessForRegisterAnonymousUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    void shouldAllowRegisterAnonymousUser() throws Exception {
        when(userService.findByEmail(any())).thenReturn(null);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register/save"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    void shouldNotAllowRegisterAnonymousUser() throws Exception {
        when(userService.findByEmail(any())).thenReturn(new User());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register/save"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));

    }
}
