package dev.lucas_a.authentication_and_authorization.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
    
}
