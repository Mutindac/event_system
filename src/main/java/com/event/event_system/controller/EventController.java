package com.event.event_system.controller;
import com.event.event_system.dto.EventDTO;
import com.event.event_system.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import com.event.event_system.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.event.event_system.service.EventService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;;


@RestController 
@RequestMapping
@RequiredArgsConstructor 
@Validated 
public class EventController {
    private final EventService eventService;

    @PostMapping ("/create_event")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody EventDTO eventDTO, @AuthenticationPrincipal User user){
        ResponseDTO response = eventService.create(eventDTO,user);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> update(@Valid @PathVariable Long id, @RequestBody EventDTO eventDTO, @AuthenticationPrincipal User user) {
        ResponseDTO response = eventService.update(eventDTO, user, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
}
