package com.gnegdev.splitast.service.manager.repository;

import com.gnegdev.splitast.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername(String username);
}