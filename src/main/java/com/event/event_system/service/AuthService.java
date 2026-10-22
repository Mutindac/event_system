package com.event.event_system.service;
import org.springframework.stereotype.Service;
import com.event.event_system.dto.ResponseDTO;
import com.event.event_system.dto.UserDTO;
import com.event.event_system.model.User;
import com.event.event_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.event.event_system.model.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
//import com.event.event_system.repository.RoleRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
   // private final RoleRepository roleRepository;

    //register
    public ResponseDTO register(UserDTO userDTO){
        //check if user exists
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            return new ResponseDTO("Error", "User already exists");
        }
        //create new user
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //set role
        //Roles role  = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(Roles.USER);
        userRepository.save(user);
        return new ResponseDTO("Success", "User registered successfully");
    }

    //login
    public ResponseDTO login(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            return new ResponseDTO("Error", "Invalid credentials");
        }
        //generate token
        String token = jwtService.generateToken(user);
        List<String> roles = List.of("ROLE_" + user.getRole().name());
        return new ResponseDTO("Success", "Login successful" + " TOKEN: " + token + " ROLE: " + roles);
    }
    //logout 
    // --> to be implemented in the future, currently we are not storing tokens in the database, so we cannot invalidate them. We can implement this by storing tokens in the database and invalidating them on logout.
}
