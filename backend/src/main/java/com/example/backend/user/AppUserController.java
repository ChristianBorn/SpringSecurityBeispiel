package com.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final UserService service;

    @GetMapping("/login")
    public String login() {
        return "ok";
    }

    @GetMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @GetMapping("/me")
    public String me() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody NewAppUser newAppUser, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new CustomApiErrorHandler(errors).getJsonString(), HttpStatus.BAD_REQUEST);
        }
        try {
            service.save(newAppUser);
            return new ResponseEntity<>("User was registered succesfully!", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
