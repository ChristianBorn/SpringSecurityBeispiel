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
            throw new UserAlreadyExistsException("""
                    {"userAlreadyExists": "User with that username already exists"}
                    """);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser appUser = new AppUser(UUID.randomUUID().toString(),
                newAppUser.username(),
                encoder.encode(newAppUser.password()),
                "Basic",
                newAppUser.eMail());
        userRepository.save(appUser);
    }

    public String getUserDetails(String username) {
        if (!username.equals("anonymousUser")) {
            String eMail;
            try {
                eMail = userRepository.findByUsername(username).eMail();
            }
            catch (NullPointerException e) {
                eMail = "";
            }

            return """
                    {"username":"<username>",
                    "eMail":"<eMail>"}
                    """.replace("<username>", username).replace("<eMail>", eMail);
        }
        return """
                    {"username":"<username>"}
                    """.replace("<username>", username);
    }
}
