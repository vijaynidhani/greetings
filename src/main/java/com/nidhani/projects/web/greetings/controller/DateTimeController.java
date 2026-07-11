package com.nidhani.projects.web.greetings.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/datetime")
public class DateTimeController {

    @GetMapping
    public Map<String, Object> getCurrentDateTime() {
        Map<String, Object> response = new HashMap<>();
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        response.put("date", now.toLocalDate().toString());
        response.put("time", now.toLocalTime().toString());
        response.put("datetime", now.format(formatter));
        response.put("timestamp", now.toInstant().toString());
        response.put("timezone", now.getZone().getId());
        response.put("offset", now.getOffset().toString());

        return response;
    }

    @GetMapping("/date")
    public Map<String, Object> getCurrentDate() {
        Map<String, Object> response = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        response.put("date", now.toLocalDate().toString());
        response.put("dayOfWeek", now.getDayOfWeek().toString());
        response.put("dayOfMonth", now.getDayOfMonth());
        response.put("month", now.getMonth().toString());
        response.put("year", now.getYear());
        response.put("dayOfYear", now.getDayOfYear());

        return response;
    }

    @GetMapping("/time")
    public Map<String, Object> getCurrentTime() {
        Map<String, Object> response = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        response.put("time", now.toLocalTime().toString());
        response.put("hour", now.getHour());
        response.put("minute", now.getMinute());
        response.put("second", now.getSecond());
        response.put("nano", now.getNano());

        return response;
    }
}