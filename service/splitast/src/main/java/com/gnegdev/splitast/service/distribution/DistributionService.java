package com.gnegdev.splitast.service.distribution;

import com.gnegdev.splitast.dto.DistributeTasksRequest;
import com.gnegdev.splitast.entity.Task;
import com.gnegdev.splitast.entity.User;
import com.gnegdev.splitast.service.manager.ReportManager;
import com.gnegdev.splitast.service.manager.UserManager;
import com.gnegdev.splitast.service.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DistributionService {
    private final ReportManager reportManager;
    private final UserRepository userRepository;

    public void distributeTasksAmongUsersFromReport(DistributeTasksRequest distributeTasksRequest) {
        List<Task> tasks = reportManager.getReportById(distributeTasksRequest.reportId()).getTasks();
        List<UUID> userIds = distributeTasksRequest.userIds();

        List<User> users = new ArrayList<>();

        for (UUID id : userIds) {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(users::add);
        }

        for (Task task : tasks) {
            User leastLoadedUser = Collections.min(users, Comparator.comparingInt((User user) -> user.getTasks().size()));

            leastLoadedUser.getTasks().add(task);
            task.setUser(leastLoadedUser);

            userRepository.save(leastLoadedUser);
        }
    }
}
