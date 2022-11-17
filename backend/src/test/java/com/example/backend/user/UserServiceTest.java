package com.example.backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository mockUserRepository = mock(UserRepository.class);

    private final BCryptPasswordEncoder mockPasswordEncorder = mock(BCryptPasswordEncoder.class);
    private final UserService userService = new UserService(mockUserRepository);


    @Test
    void saveSuccessful() {
        NewAppUser newAppUser = new NewAppUser("username", "password", "Test@Test.de");
        AppUser appUser = new AppUser(
                "id", "username", "password", "role", "eMail"
        );
            when(mockUserRepository.save(appUser)).thenReturn(appUser);
            when(mockPasswordEncorder.encode("password")).thenReturn("encodedPassword");
            String actual = userService.save(newAppUser, mockPasswordEncorder);
            String expected = "Created user: " + newAppUser.username();
            //then
            assertEquals(expected, actual);
            verify(mockPasswordEncorder).encode("password");
        }


    @Test
    void getUserDetails() {
    }
}