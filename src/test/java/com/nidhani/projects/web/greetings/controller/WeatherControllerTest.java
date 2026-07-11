package com.nidhani.projects.web.greetings.controller;

import com.nidhani.projects.web.greetings.GreetingsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private org.springframework.web.client.RestTemplate restTemplate;

    @Test
    void getWeatherForValidCity() throws Exception {
        Map<String, Object> main = new HashMap<>();
        main.put("temp", 25.5);
        main.put("feels_like", 26.0);
        main.put("humidity", 60);
        main.put("pressure", 1013);
        main.put("temp_min", 24.0);
        main.put("temp_max", 27.0);

        Map<String, Object> wind = new HashMap<>();
        wind.put("speed", 5.5);
        wind.put("deg", 180);

        Map<String, Object> sys = new HashMap<>();
        sys.put("country", "US");

        Map<String, Object> weather = new HashMap<>();
        weather.put("description", "clear sky");
        weather.put("main", "Clear");
        weather.put("icon", "01d");

        Map<String, Object> response = new HashMap<>();
        response.put("name", "New York");
        response.put("main", main);
        response.put("wind", wind);
        response.put("sys", sys);
        response.put("weather", List.of(weather));
        response.put("visibility", 10000);
        response.put("dt", 1699900000L);

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(response);

        mockMvc.perform(get("/weather").param("city", "New York"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.city").value("New York"))
                .andExpect(jsonPath("$.country").value("US"))
                .andExpect(jsonPath("$.temperature").value(25.5))
                .andExpect(jsonPath("$.feelsLike").value(26.0))
                .andExpect(jsonPath("$.humidity").value(60))
                .andExpect(jsonPath("$.pressure").value(1013))
                .andExpect(jsonPath("$.description").value("clear sky"))
                .andExpect(jsonPath("$.windSpeed").value(5.5))
                .andExpect(jsonPath("$.visibility").value(10000));
    }

    @Test
    void getWeatherForCityNotFound() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(null);

        mockMvc.perform(get("/weather").param("city", "NonExistentCity"))
                .andExpect(status().isNotFound());
    }
}