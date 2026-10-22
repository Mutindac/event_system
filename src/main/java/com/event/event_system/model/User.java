package com.event.event_system.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true,name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    // @Column(nullable = true, name = "mobile_number")
    // private String mobileNumber;
    @CreationTimestamp 
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Roles role;
}
