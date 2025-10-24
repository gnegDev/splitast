package com.gnegdev.splitast.controller;

import com.gnegdev.splitast.dto.DistributeTasksRequest;
import com.gnegdev.splitast.service.distribution.DistributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/splitast/api/distribute")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionService distributionService;

    @PostMapping
    public ResponseEntity<?> distributeTasksAmongUsersFromReport(@Valid @RequestBody DistributeTasksRequest distributeTasksRequest) {
        try {
            distributionService.distributeTasksAmongUsersFromReport(distributeTasksRequest);
            return ResponseEntity.ok("Tasks distributed successfully from report with id: " + distributeTasksRequest.reportId());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
