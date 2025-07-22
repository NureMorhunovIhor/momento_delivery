package org.example.momento_delivery.controllers;

import org.example.momento_delivery.dtos.RegisterRequest;
import org.example.momento_delivery.entities.User;
import org.example.momento_delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.of(userRepository.findByEmail(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Authentication auth, @RequestBody RegisterRequest update) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setName(update.name);
        user.setPhone(update.phone);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(Authentication auth) {
        String email = auth.getName();
        userRepository.deleteByEmail(email);
        return ResponseEntity.ok("User deleted");
    }
}

