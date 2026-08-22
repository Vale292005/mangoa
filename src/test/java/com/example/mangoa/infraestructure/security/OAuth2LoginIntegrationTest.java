package com.example.mangoa.infraestructure.security;

import com.example.mangoa.application.domain.Repositories.UserRepository;
import com.example.mangoa.application.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "application.security.jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970",
    "application.security.jwt.expiration=86400000",
    "jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970",
    "jwt.expiration=86400000"
})
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class OAuth2LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Debe autenticar usuario con Google OAuth2, registrarlo en la BD y redirigir con JWT")
    void shouldLoginWithGoogleAndRedirectWithJwt() throws Exception {
        String testEmail = "valeria.test@gmail.com";
        Map<String, Object> attributes = Map.of(
                "email", testEmail,
                "given_name", "Valeria",
                "family_name", "Florez");

        OAuth2User mockGoogleUser = new DefaultOAuth2User(
                Collections.emptyList(),
                attributes,
                "email");

        mockMvc.perform(get("/login/oauth2/code/google")
                .with(oauth2Login().oauth2User(mockGoogleUser)))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",
                        org.hamcrest.Matchers.startsWith("http://localhost:5173/oauth2/redirect?token=")));

        Optional<User> savedUser = userRepository.findByEmail(testEmail);
        assertTrue(savedUser.isPresent(), "El usuario debería guardarse en la BD");
        assertEquals("Valeria", savedUser.get().getFirstName());
        assertEquals(User.Role.CUSTOMER, savedUser.get().getRole());
    }
}