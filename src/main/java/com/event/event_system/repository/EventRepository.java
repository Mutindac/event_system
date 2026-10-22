package com.event.event_system.repository;
import com.event.event_system.model.Event;
import com.event.event_system.model.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    //find event by id and user
    Optional<Event> findByIdAndUser(Long id, User user);
    //get event by id
    //Event event
    //list all event
    List<Event> findAll();
}
