package com.gnegdev.splitast.service.manager;

import com.gnegdev.splitast.dto.CreateUserRequest;
import com.gnegdev.splitast.entity.Role;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.manager.repository.UserRepository;
import com.gnegdev.splitast.util.mapper.UserMapper;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(UUID uuid) throws NoResultException {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new NoResultException("User not found with id: " + uuid));
    }

    public User getUserByUsername(String username) throws NoResultException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoResultException("User not found with username: " + username));
    }

    public User getUserByEmail(String email) throws NoResultException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoResultException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);

        user.setPassword(passwordEncoder.encode(createUserRequest.password()));
        user.setRole(Role.ROLE_USER);

        return userRepository.save(user);
    }
}
