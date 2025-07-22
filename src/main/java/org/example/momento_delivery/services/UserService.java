package org.example.momento_delivery.services;

import org.example.momento_delivery.dtos.RegisterRequest;
import org.example.momento_delivery.entities.User;
import org.example.momento_delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("email = " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
        );
    }

    public User register(RegisterRequest request, PasswordEncoder encoder) {
        User user = new User();
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setName(request.name);
        user.setPasswordHash(encoder.encode(request.password));
        return userRepository.save(user);
    }
}
