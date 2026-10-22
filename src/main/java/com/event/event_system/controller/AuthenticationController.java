package com.event.event_system.controller;
import com.event.event_system.dto.UserDTO;
import com.event.event_system.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.event.event_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/auth")
@RequiredArgsConstructor 
@Validated 
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserDTO userDTO){
        ResponseDTO response = authService.register(userDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody UserDTO userDTO){
        ResponseDTO response = authService.login(userDTO);
        return ResponseEntity.ok(response);
    }
}
