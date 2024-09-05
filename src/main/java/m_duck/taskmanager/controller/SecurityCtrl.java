package m_duck.taskmanager.controller;

import m_duck.taskmanager.DTO.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityCtrl {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest login) {
        System.out.println(login.toString());
        System.out.println(SecurityContextHolder.getContext().toString());
        return ResponseEntity.ok(SecurityContextHolder.getContext().toString());
    }

}

