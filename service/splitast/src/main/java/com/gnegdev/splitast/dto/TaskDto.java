package com.gnegdev.splitast.dto;

public record TaskDto(
        String severity,
        String message,
        String uri,
        Integer line
) {
}
