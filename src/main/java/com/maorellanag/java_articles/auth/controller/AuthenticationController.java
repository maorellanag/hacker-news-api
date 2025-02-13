package com.maorellanag.java_articles.auth.controller;

import com.maorellanag.java_articles.auth.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(
        summary = "Authenticate user and generate JWT token",
        description = "Authenticates the user through basic AUTH and returns a JWT token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "JWT token generated successfully"),
        @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    @PostMapping("/token")
    public ResponseEntity<String> authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String jwtToken = jwtService.generateToken(name);

        return ResponseEntity.ok(jwtToken);
    }

}
