package com.productsAPI.service;

import com.productsAPI.dto.UserDTO;
import com.productsAPI.model.User;
import com.productsAPI.repository.UserRepository;
import com.productsAPI.utils.PasswordUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
    }

    public User registerUser(UserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail already registered!");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(User.Role.CLIENT);

        String encodedPassword = passwordUtil.encodePassword(dto.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }


    public void promoteToAdmin(Long userId) {
        UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new RuntimeException("You are not allowed to promote users.");
        }

        User userToPromote = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        userToPromote.setRole(User.Role.ADMIN);
        userRepository.save(userToPromote);
    }



    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
