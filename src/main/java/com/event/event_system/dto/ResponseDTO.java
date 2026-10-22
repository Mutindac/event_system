package com.event.event_system.dto;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String status;
    private String message;
}
