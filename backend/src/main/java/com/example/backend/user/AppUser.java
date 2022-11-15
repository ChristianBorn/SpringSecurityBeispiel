package com.example.backend.user;

import com.mongodb.lang.Nullable;



public record AppUser(
        String id,
        String username,
        String passwordBcrypt,
        String role,
        @Nullable
        String eMail
) {
}
