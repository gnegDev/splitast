package com.gnegdev.splitast.dto;

import com.gnegdev.splitast.entity.Task;

import java.util.List;

public record ParseReportResponse(
        List<Task> tasks
) {
}
