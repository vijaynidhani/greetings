package com.nidhani.projects.web.greetings.controller;

import com.nidhani.projects.web.greetings.GreetingsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DateTimeController.class)
class DateTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCurrentDateTime() throws Exception {
        MvcResult result = mockMvc.perform(get("/datetime"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.datetime").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timezone").exists())
                .andReturn();
    }

    @Test
    void getCurrentDate() throws Exception {
        mockMvc.perform(get("/datetime/date"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.dayOfWeek").exists())
                .andExpect(jsonPath("$.dayOfMonth").exists())
                .andExpect(jsonPath("$.month").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.dayOfYear").exists());
    }

    @Test
    void getCurrentTime() throws Exception {
        mockMvc.perform(get("/datetime/time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.hour").exists())
                .andExpect(jsonPath("$.minute").exists())
                .andExpect(jsonPath("$.second").exists())
                .andExpect(jsonPath("$.nano").exists());
    }
}