package com.gnegdev.splitast.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Username must be provided.")
        @Size(max = 255, message = "Username must be shorter than 255 characters.")
        String username,

        @NotBlank(message = "Password must be provided")
        @Size(min = 8, max = 32, message = "Password must be longer than 8 and shorter than 32 characters.")
        String password,

        @NotBlank(message = "Email must be provided")
        @Size(max = 255, message = "Email must be shorter than 255 characters.")
        String email
) {
}
