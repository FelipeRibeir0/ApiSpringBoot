package com.productsAPI.controller;

import com.productsAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Promote user to ADMIN", description = "Promote a specific user to ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully promoted to ADMIN."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid user data."),
            @ApiResponse(responseCode = "403", description = "Forbidden: Only ADMINs can promote users."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/promote/{userId}", headers = "X-API-Version=v1")
    public ResponseEntity<String> promoteUser(@PathVariable Long userId) {
        try {
            userService.promoteToAdmin(userId);
            return ResponseEntity.ok("User successfully promoted to ADMIN.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
