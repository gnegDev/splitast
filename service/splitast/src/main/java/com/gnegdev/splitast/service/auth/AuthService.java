package com.gnegdev.splitast.service.auth;

import com.gnegdev.splitast.dto.LoginUserRequest;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.manager.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;

    public boolean authenticateUser(LoginUserRequest loginUserRequest) {
        User user = userManager.getUserByEmail(loginUserRequest.email());

        return passwordEncoder.matches(loginUserRequest.rawPassword(), user.getPassword());
    }
}
