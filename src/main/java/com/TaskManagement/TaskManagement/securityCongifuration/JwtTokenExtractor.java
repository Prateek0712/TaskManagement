package com.TaskManagement.TaskManagement.securityCongifuration;

import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenExtractor {
    public static String extractToken(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token by removing "Bearer " prefix
            return authHeader.substring(7);
        }

        // No JWT token found in the Authorization header
        return null;
    }

}
