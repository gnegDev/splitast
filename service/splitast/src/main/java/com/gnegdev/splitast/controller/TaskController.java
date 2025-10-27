package com.gnegdev.splitast.controller;

import com.gnegdev.splitast.entity.Report;
import com.gnegdev.splitast.entity.Task;
import com.gnegdev.splitast.service.manager.TaskManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/splitast/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskManager taskManager;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable UUID id) {
        try {
            Task task = taskManager.getTaskById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
