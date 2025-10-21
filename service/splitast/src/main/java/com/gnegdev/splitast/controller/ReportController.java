package com.gnegdev.splitast.controller;

import com.gnegdev.splitast.dto.CreateReportRequest;
import com.gnegdev.splitast.entity.Report;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.manager.ReportManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/splitast/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportManager reportManager;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        try {
            Report report = reportManager.getReportById(id);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReport(@Valid @ModelAttribute CreateReportRequest createReportRequest) throws IOException {
        Report report = reportManager.createReport(createReportRequest);

        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
