package com.gnegdev.splitast.controller;

import com.gnegdev.splitast.dto.CreateUserRequest;
import com.gnegdev.splitast.dto.LoginUserRequest;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.auth.AuthService;
import com.gnegdev.splitast.service.manager.UserManager;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/splitast/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserManager userManager;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        try {
            User user = userManager.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userManager.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        try {
            if (authService.authenticateUser(loginUserRequest)) {
                User user = userManager.getUserByEmail(loginUserRequest.email());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userManager.createUser(createUserRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
