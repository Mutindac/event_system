package com.event.event_system.security;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.security.Security;
import com.event.event_system.service.JwtService;
import lombok.RequiredArgsConstructor;
import com.event.event_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component 
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get the token from the header
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // get the token
        String token = authHeader.substring(7);
        // validate the token
        if(!jwtService.isTokenValid(token)){
            filterChain.doFilter(request, response);
            return;
        }    

        try{
            String email = jwtService.extractEmail(token);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (email != null && auth == null) {
                userRepository.findByEmail(email).ifPresent(user ->{
                    List<SimpleGrantedAuthority> authorities = jwtService.extractRoles(token).stream().map(SimpleGrantedAuthority::new).toList();
                    UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(user,null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authtoken);
                });
            }
            filterChain.doFilter(request, response);
        } catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, e, e);
        }

    }
}
