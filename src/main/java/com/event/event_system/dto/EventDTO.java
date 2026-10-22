package com.event.event_system.dto;
import lombok.Data;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class EventDTO {
    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Event location is required")
    private String location;

    @NotBlank(message = "Event category is required")
    private String category;

    @NotBlank(message = "Event description is required")
    private String description;

    @NotNull (message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull (message = "Start time is required")
    private LocalDateTime endTime;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
}
