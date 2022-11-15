package com.example.backend;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("myApp.baseURL", () -> mockWebServer.url("/").toString());
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

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

    @Test
    @WithMockUser(roles = "Basic")
    void expect200_andOk_whenAuthenticatedAndAuthorised() throws Exception {
        mockMvc.perform(get("/api/app-users/login"))
                .andExpect(status().is(200))
                .andExpect(content().string("ok"));
    }

    @Test
    @WithMockUser(roles = "Basic")
    void expect200_OnLogout_whenAuthenticatedAndAuthorised() throws Exception {
        mockMvc.perform(get("/api/app-users/logout"))
                .andExpect(status().is(200));
    }


    @Test
    void expect200_and_anonymousUser() throws Exception {
        mockMvc.perform(get("/api/app-users/me"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                                                    {"username": "anonymousUser"}
                                                    """));
    }

    @Test
    @WithMockUser(username = "Test", roles = "Basic")
    void expect200_and_UserDetails() throws Exception {
        mockMvc.perform(get("/api/app-users/me"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                                                    {"username": "Test","eMail": ""}
                                                    """));
}
}
