package com.event.event_system.repository;
import com.event.event_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository 
public interface UserRepository extends JpaRepository<User, Long> {
    //find user by email
    Optional<User> findByEmail(String email);

    //find user by id
    User findById(long id);
}
