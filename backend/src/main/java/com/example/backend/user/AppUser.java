package com.example.backend.user;

public record AppUser(
        String id,
        String username,
        String passwordBcrypt,
        String role
) {
}
