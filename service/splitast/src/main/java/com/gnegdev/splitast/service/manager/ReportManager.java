package com.gnegdev.splitast.service.manager;

import com.gnegdev.splitast.dto.CreateReportRequest;
import com.gnegdev.splitast.dto.TaskDto;
import com.gnegdev.splitast.entity.Report;
import com.gnegdev.splitast.entity.Status;
import com.gnegdev.splitast.entity.Task;
import com.gnegdev.splitast.service.manager.repository.ReportRepository;
import com.gnegdev.splitast.service.parser.ReportParserService;
import com.gnegdev.splitast.service.s3.S3ClientService;
import com.gnegdev.splitast.util.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReportManager {
    private final ReportRepository reportRepository;
    private final ReportParserService reportParserService;
    private final S3ClientService s3ClientService;
    private final TaskMapper taskMapper;

    public Report getReportById(UUID uuid) {
        return reportRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + uuid));
    }

    public Report createReport(CreateReportRequest createReportRequest) throws IOException {
        Report report = new Report();
        List<TaskDto> parsedTasks = reportParserService.parseSarif(createReportRequest.file());

        report.setDescription(createReportRequest.description());
        for (TaskDto taskDto : parsedTasks) {
            Task task = taskMapper.toEntity(taskDto);
            task.setReport(report);
            task.setStatus(Status.STATUS_NEW);

            report.getTasks().add(task);
        }

        String filename = s3ClientService.uploadFile(createReportRequest.file());
        report.setFilename(filename);

        try {
            return reportRepository.save(report);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
