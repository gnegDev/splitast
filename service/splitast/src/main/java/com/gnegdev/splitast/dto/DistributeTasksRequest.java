package com.gnegdev.splitast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record DistributeTasksRequest (
        @NotNull
        @JsonProperty("report_id")
        UUID reportId,

        @NotEmpty
        @JsonProperty("user_ids")
        List<UUID> userIds
){
}
