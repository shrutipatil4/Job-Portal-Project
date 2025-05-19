package com.capgemini.job_application;

import com.capgemini.job_application.controllers.UserController;

import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final User user = new User(1L, "Alice", "alice@example.com", "1234567890", "password", "Address", "USER", 25, "Female");

    @Test
    void getAll_shouldReturnUserList() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("Alice"))
                .andExpect(jsonPath("$[0].userEmail").value("alice@example.com"));
    }

    @Test
    void getById_shouldReturnUserIfExists() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Alice"));
    }

    @Test
    void getById_shouldReturnErrorIfNotFound() throws Exception {
        Mockito.when(userService.getUserById(2L)).thenThrow(new RuntimeException("No user found with ID: 2"));

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createUser_shouldCreateAndReturnUser() throws Exception {
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        String userJson = """
                {
                  "userName": "Alice",
                  "userEmail": "alice@example.com",
                  "phone": "1234567890",
                  "password": "A@1password",
                  "address": "Address",
                  "userType": "USER",
                  "age": 25,
                  "gender": "Female"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("Alice"));
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() throws Exception {
        User updated = new User(1L, "Bob", "bob@example.com", "0987654321", "newpass", "New Address", "ADMIN", 30, "Male");
        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        String userJson = """
                {
                  "userName": "Bob",
                  "userEmail": "bob@example.com",
                  "phone": "0987654321",
                  "password": "newN@pass123",
                  "address": "New Address",
                  "userType": "ADMIN",
                  "age": 30,
                  "gender": "Male"
                }
                """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.userName").value("Bob"));
    }
    
    @Test
    void createUser_shouldReturnBadRequestForInvalidPassword() throws Exception {
        String invalidUserJson = """
                {
                  "userName": "Parikshit",
                  "userEmail": "parikshit.jadhav@example.com",
                  "phone": "1758385987",
                  "password": "MySecure123",
                  "address": "Pune",
                  "userType": "applicant",
                  "age": 25,
                  "gender": "Male"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}

