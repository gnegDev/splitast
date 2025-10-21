package com.gnegdev.splitast.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CreateReportRequest(
        @NotNull
        MultipartFile file,

        @NotNull
        String description
) {
}
