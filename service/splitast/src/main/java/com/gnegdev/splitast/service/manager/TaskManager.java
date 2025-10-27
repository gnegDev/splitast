package com.gnegdev.splitast.service.manager;

import com.gnegdev.splitast.entity.Task;
import com.gnegdev.splitast.service.manager.repository.TaskRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskManager {
    private final TaskRepository taskRepository;

    public Task getTaskById(UUID uuid) throws NoResultException {
        return taskRepository.findById(uuid)
                .orElseThrow(() -> new NoResultException("Task not found with id: " + uuid));
    }
}
