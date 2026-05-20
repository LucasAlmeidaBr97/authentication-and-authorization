package dev.lucas_a.authentication_and_authorization.config;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import dev.lucas_a.authentication_and_authorization.entity.User;

@Component
public class TokenConfig {

    
    private String secret = "secret";

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user){
        return JWT.create()
            .withClaim("userId", user.getId())
            .withSubject(user.getEmail())
            .withExpiresAt(Instant.now().plusSeconds(86400))
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }
}
