package com.example.backend.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record NewAppUser(

        @NotBlank
        @Length(min = 3)
        String username,
        @NotBlank //(message = "Password is required")
        @Length(min = 8)
        @Pattern(regexp = "^(?=[^A-Z]*+[A-Z])(?=[^a-z]*+[a-z])(?=\\D*+\\d)(?=[^#?!@$ %^&*-]*+[#?!@$ %^&*-]).{8,}$", message = "Password must have minimum of eight characters, at least one letter and one number!")
        String password) {
}
