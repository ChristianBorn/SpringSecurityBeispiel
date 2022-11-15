package com.example.backend.user;

import com.mongodb.lang.Nullable;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public record NewAppUser(


        @Length(min = 3)
        String username,
//        @NotBlank(message = "Password cannot be empty!")
//        @Length(min = 8)
        @Pattern(regexp = "^(?=[^A-Z]*+[A-Z])(?=[^a-z]*+[a-z])(?=\\D*+\\d)(?=[^#?!@$ %^&*-]*+[#?!@$ %^&*-]).{8,}$",
                message = "Password must have minimum of eight characters, at least one letter, one special character and one number!")
        String password,
        @Nullable
        String eMail

) {
}
