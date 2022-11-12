package com.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(NewAppUser newAppUser) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(newAppUser.username()) != null) {
            throw new UserAlreadyExistsException("User with that username already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser appUser = new AppUser(UUID.randomUUID().toString(),
                newAppUser.username(),
                encoder.encode(newAppUser.password()),
                "Basic");
        userRepository.save(appUser);
    }
}
