package com.gnegdev.splitast.service.manager;

import com.gnegdev.splitast.service.manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskManager {
    private final TaskRepository taskRepository;

}
