package com.nidhani.projects.web.greetings.controller;

import com.nidhani.projects.web.greetings.GreetingsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greetingWithDefaultName() throws Exception {
        MvcResult result = mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Hello, World!"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andReturn();
    }

    @Test
    void greetingWithCustomName() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Hello, John!"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void greetingWithPathVariable() throws Exception {
        mockMvc.perform(get("/greeting/Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Hello, Alice!"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("greetings-service"));
    }
}