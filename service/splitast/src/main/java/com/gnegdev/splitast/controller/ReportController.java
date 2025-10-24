package com.gnegdev.splitast.controller;

import com.gnegdev.splitast.dto.CreateReportRequest;
import com.gnegdev.splitast.entity.Report;
import com.gnegdev.splitast.service.manager.ReportManager;
import com.gnegdev.splitast.service.s3.S3ClientService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final S3ClientService s3ClientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable UUID id) {
        try {
            Report report = reportManager.getReportById(id);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> getReportFile(@PathVariable String filename) {
        try {
            byte[] data = s3ClientService.getFileBytes(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .body(data);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

        @PostMapping("/create")
    public ResponseEntity<?> createReport(@Valid @ModelAttribute CreateReportRequest createReportRequest) throws IOException {
        Report report = reportManager.createReport(createReportRequest);

        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
