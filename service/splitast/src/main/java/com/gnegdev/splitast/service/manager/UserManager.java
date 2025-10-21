package com.gnegdev.splitast.service.manager;

import com.gnegdev.splitast.dto.CreateUserRequest;
import com.gnegdev.splitast.entity.Role;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.manager.repository.UserRepository;
import com.gnegdev.splitast.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUserById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uuid));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);
        user.setRole(Role.ROLE_USER);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
