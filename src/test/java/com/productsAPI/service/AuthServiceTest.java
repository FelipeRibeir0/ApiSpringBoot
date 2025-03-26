package com.productsAPI.service;

import com.productsAPI.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private final String email = "user@example.com";
    private final String password = "password123";
    private final String role = "CLIENT";

    @Nested
    class WhenCredentialsAreValid {
        @BeforeEach
        void setUp() {
            Authentication authentication = mock(Authentication.class);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);

            when(jwtUtil.generateToken(email, role)).thenReturn("mocked-jwt-token");
        }

        @Test
        void shouldReturnToken() {
            String result = authService.login(email, password, role);

            assertNotNull(result);
            assertEquals("mocked-jwt-token", result);

            verify(jwtUtil).generateToken(email, role);
        }

        @Test
        void shouldCallAuthenticationManagerWithCorrectCredentials() {
            authService.login(email, password, role);

            ArgumentCaptor<UsernamePasswordAuthenticationToken> captor =
                    ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

            verify(authenticationManager).authenticate(captor.capture());

            UsernamePasswordAuthenticationToken authToken = captor.getValue();
            assertEquals(email, authToken.getPrincipal());
            assertEquals(password, authToken.getCredentials());
        }
    }

    @Nested
    class WhenCredentialsAreInvalid {
        @Test
        void shouldThrowExceptionWhenAuthenticationFails() {
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(email, password, role));
        }

        @Test
        void shouldThrowExceptionWhenNotAuthenticated() {
            Authentication authentication = mock(Authentication.class);
            when(authentication.isAuthenticated()).thenReturn(false);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(email, password, role));
        }
    }
}