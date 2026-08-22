package com.example.mangoa.application.security.oauth2;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.mangoa.application.domain.Repositories.UserRepository;
import com.example.mangoa.application.domain.model.User;
import com.example.mangoa.application.security.jwt.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
        ) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.create(
                            email,
                            "OAUTH2_PASSWORD_PLACEHOLDER",
                            User.Role.CUSTOMER,
                            firstName != null ? firstName : "Usuario",
                            lastName != null ? lastName : "Google");
                    return userRepository.save(newUser);
                });
            
            String token =jwtService.generateToken(user);

            String targetUrl = "http://localhost:5173/oauth2/redirect?token=" + token;
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
