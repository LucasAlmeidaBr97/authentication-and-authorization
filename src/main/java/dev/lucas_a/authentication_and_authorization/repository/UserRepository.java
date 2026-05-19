package dev.lucas_a.authentication_and_authorization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import dev.lucas_a.authentication_and_authorization.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<UserDetails> findUserByEmail(String username);

    
}
