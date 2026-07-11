package com.nidhani.projects.web.greetings.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherController(RestTemplate restTemplate, @Value("${openweathermap.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCurrentWeather(@RequestParam String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                return ResponseEntity.notFound().build();
            }

            Map<String, Object> weatherData = extractWeatherData(response);
            return ResponseEntity.ok(weatherData);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch weather data");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private Map<String, Object> extractWeatherData(Map<String, Object> response) {
        Map<String, Object> weatherData = new HashMap<>();

        @SuppressWarnings("unchecked")
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        @SuppressWarnings("unchecked")
        Map<String, Object> wind = (Map<String, Object>) response.get("wind");
        @SuppressWarnings("unchecked")
        Map<String, Object> sys = (Map<String, Object>) response.get("sys");
        @SuppressWarnings("unchecked")
        java.util.List<Map<String, Object>> weatherList = (java.util.List<Map<String, Object>>) response.get("weather");

        Map<String, Object> weather = weatherList != null && !weatherList.isEmpty() ? weatherList.get(0) : new HashMap<>();

        weatherData.put("city", response.get("name"));
        weatherData.put("country", sys.get("country"));
        weatherData.put("temperature", main.get("temp"));
        weatherData.put("feelsLike", main.get("feels_like"));
        weatherData.put("minTemperature", main.get("temp_min"));
        weatherData.put("maxTemperature", main.get("temp_max"));
        weatherData.put("pressure", main.get("pressure"));
        weatherData.put("humidity", main.get("humidity"));
        weatherData.put("description", weather.get("description"));
        weatherData.put("icon", weather.get("icon"));
        weatherData.put("windSpeed", wind.get("speed"));
        weatherData.put("windDirection", wind.get("deg"));
        weatherData.put("visibility", response.get("visibility"));

        return weatherData;
    }
}