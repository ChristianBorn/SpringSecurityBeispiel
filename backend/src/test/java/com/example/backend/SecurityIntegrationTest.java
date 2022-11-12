package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void expect401_whenNoAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/cats"))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser
    void expect403_whenAuthenticatedButUnauthorised() throws Exception {
        mockMvc.perform(get("/api/cats"))
                .andExpect(status().is(403));
    }
    @Test
    @WithMockUser(roles = "Basic")
    void expect200_whenAuthenticatedAndAuthorised() throws Exception {
        mockMvc.perform(get("/api/cats"))
                .andExpect(status().is(200));
    }
}
