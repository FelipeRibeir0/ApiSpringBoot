package com.productsAPI.service;

import com.productsAPI.dto.UserDTO;
import com.productsAPI.model.User;
import com.productsAPI.repository.UserRepository;
import com.productsAPI.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User user;
    private UserDTO userDTO;
    private User userToPromote;
    private UserDetails adminUser;
    private UserDetails regularUser;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(User.Role.CLIENT);
        user.setPassword("encodedPassword");

        userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPassword("rawPassword");

        userToPromote = new User();
        userToPromote.setId(2L);
        userToPromote.setName("David Mills");
        userToPromote.setEmail("mills@example.com");
        userToPromote.setRole(User.Role.CLIENT);

        adminUser = new org.springframework.security.core.userdetails.User(
                "admin@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        regularUser = new org.springframework.security.core.userdetails.User(
                "user@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordUtil.encodePassword(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.registerUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john@example.com", createdUser.getEmail());
        assertEquals(User.Role.CLIENT, createdUser.getRole());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(userRepository).findByEmail(userDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_FailsWhenEmailExists() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(userDTO));
        assertEquals("E-mail already registered!", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByEmail_Success() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail("john@example.com");

        assertNotNull(foundUser);
        assertEquals("john@example.com", foundUser.getEmail());

        verify(userRepository).findByEmail("john@example.com");
    }

    @Test
    void findByEmail_UserNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.findByEmail("notfound@example.com"));

        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findByEmail("notfound@example.com");
    }

    @Test
    void promoteToAdmin_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(adminUser);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findById(2L)).thenReturn(Optional.of(userToPromote));

        userService.promoteToAdmin(2L);

        assertEquals(User.Role.ADMIN, userToPromote.getRole());
        verify(userRepository).save(userToPromote);
    }

    @Test
    void promoteToAdmin_FailsWhenUserNotFound() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(adminUser);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.promoteToAdmin(2L));
        assertEquals("User not found.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void promoteToAdmin_FailsWhenUserHasNoPermission() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(regularUser);
        SecurityContextHolder.setContext(securityContext);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.promoteToAdmin(2L));
        assertEquals("You are not allowed to promote users.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

}