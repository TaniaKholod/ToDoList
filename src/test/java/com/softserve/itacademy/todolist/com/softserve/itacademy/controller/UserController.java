package com.softserve.itacademy.todolist.com.softserve.itacademy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.datasource.url=jdbc:postgresql://localhost:5432/todo_test" })
public class UserController {
    /*
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    private final static long USERID = 5L;

    @Test
    public void getUserTest() throws Exception {
        User expectedUser = userService.readById(USERID);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{user_id}/read", USERID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expectedUser));
    }

    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", userService.getAll()));
    }

    @Test
    public void createUserGetMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()));
    }

    @Test
    @Transactional
    public void createUserPostMethodTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName", "Tania")
                        .param("lastName", "Kholod")
                        .param("email", "tania.kholod@gmail.com")
                        .param("password", "1111"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        assertEquals("Tania", userService.readByEmail("tania.kholod@gmail.com").getFirstName());
    }

    @Test
    void createUserPOSTmethodWhenFormHasErrorsTest() throws Exception {
        String emptyFirstName = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName", emptyFirstName)
                        .param("lastName", "Lastname")
                        .param("email", "user@mail.com")
                        .param("password", "1111"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(1))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("user", "firstName", "Pattern"));
        assertNull(userService.readByEmail("user@mail.com"));
    }

    @Test
    void updateUserGetMethodTest() throws Exception {
        User expectedUser = userService.readById(USERID);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{user_id}/update", USERID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", expectedUser));
    }

    @Test
    @Transactional
    void updateUserPostMethodTest() throws Exception {
        String newPassword = "1111";
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{user_id}/update", USERID)
                        .param("password", newPassword)
                        .param("roleId", String.valueOf(1L)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertEquals(newPassword, userService.readById(USERID).getPassword());
    }

    @Test
    void updateUserPOSTmethodWhenFormHasErrorsTest() throws Exception {
        String emptyFirstName = "";
        User userBeforeUpdate = userService.readById(USERID);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{user_id}/update", USERID)
                        .param("id", String.valueOf(USERID))
                        .param("firstName", emptyFirstName)
                        .param("lastName", "Lastname")
                        .param("email", "user@mail.com")
                        .param("password", "1111")
                        .param("roleId", String.valueOf(1L)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(1))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("user", "firstName", "Pattern"));

        User userAfterUpdate = userService.readById(USERID);
        assertEquals(userBeforeUpdate, userAfterUpdate);
    }

    @Test
    @Transactional
    void deleteUserPostMethodTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{user_id}/delete", USERID))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> userService.readById(USERID));

        assertTrue(thrown.getMessage().contains("not found"));

    }

     */
}
