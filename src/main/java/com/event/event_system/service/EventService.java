package com.event.event_system.service;
import com.event.event_system.repository.EventRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import com.event.event_system.dto.EventDTO;
import com.event.event_system.dto.ResponseDTO;
import com.event.event_system.model.Event;
import com.event.event_system.model.Roles;
import com.event.event_system.model.User;
//import com.event.event_system.repository.UserRepository;
import lombok.*;

@Service 
@RequiredArgsConstructor 
public class EventService {
    private final EventRepository eventRepository;
    //private final UserRepository userRepository;

    @Transactional 
    public ResponseDTO create(EventDTO eventDTO, User user){
        Event event = new Event();
        applyDto(event, eventDTO);
        event.setUser(user);
        eventRepository.save(event);
        return new ResponseDTO("success", "event created successfully");
    }

    @Transactional 
    public ResponseDTO update(EventDTO eventDTO, User user, Long id){
        //get event
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        boolean isOwner = event.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Roles.ADMIN;
        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot update this event");
        }

        if (eventDTO.getEndTime() != null && eventDTO.getEndTime().isBefore(eventDTO.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time");
        }
        applyDto(event, eventDTO);
        eventRepository.save(event);
        return new ResponseDTO("success", "event updated successfully");
    }

    @Transactional 
    public List<EventDTO> getAllEvents() {
        List<EventDTO> result = new ArrayList<>();
        for (Event event : eventRepository.findAll()) {
            EventDTO dto = new EventDTO();
            dto.setName(event.getName());
            dto.setLocation(event.getLocation());
            dto.setCategory(event.getCategory());
            dto.setDescription(event.getDescription());
            dto.setStartTime(event.getStartTime());
            dto.setEndTime(event.getEndTime());
            dto.setCapacity(event.getCapacity());
            result.add(dto);
        }
        return result;
    }

    @Transactional 
    public EventDTO getById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        EventDTO dto = new EventDTO();
        dto.setName(event.getName());
        dto.setLocation(event.getLocation());
        dto.setCategory(event.getCategory());
        dto.setDescription(event.getDescription());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setCapacity(event.getCapacity());
        return dto;
    }

    private void applyDto(Event event, EventDTO dto) {
        event.setName(dto.getName());
        event.setLocation(dto.getLocation());
        event.setCategory(dto.getCategory());
        event.setDescription(dto.getDescription());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setCapacity(dto.getCapacity());
    }
}
