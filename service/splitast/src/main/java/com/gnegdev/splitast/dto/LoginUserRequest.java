package com.gnegdev.splitast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank
        String email,
        @NotBlank
        @JsonProperty("password")
        String rawPassword
) {
}
