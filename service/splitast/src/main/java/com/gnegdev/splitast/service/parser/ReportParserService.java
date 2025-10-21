package com.gnegdev.splitast.service.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnegdev.splitast.dto.CreateReportRequest;
import com.gnegdev.splitast.dto.TaskDto;
import com.gnegdev.splitast.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportParserService {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<TaskDto> parseSarif(MultipartFile file) {
        List<TaskDto> tasks = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(file.getInputStream());
            JsonNode results = root.path("runs").get(0).path("results");

            for (JsonNode result : results) {
//                String ruleId = result.path("ruleId").asText();
                String message = result.path("message").path("text").asText();
                String severity = result.path("level").asText("warning");

                JsonNode location = result.path("locations").get(0).path("physicalLocation");
                String uri = location.path("artifactLocation").path("uri").asText();
                int line = location.path("region").path("startLine").asInt();

                TaskDto task = new TaskDto(
                        severity,
                        message,
                        uri,
                        line
                );

                tasks.add(task);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при парсинге SARIF: " + e.getMessage(), e);
        }

        return tasks;
    }
}
