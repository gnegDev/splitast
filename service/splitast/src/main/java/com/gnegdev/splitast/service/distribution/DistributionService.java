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
    private final UserManager userManager;
    private final UserRepository userRepository;

    public void distributeTasksAmongUsersFromReport(DistributeTasksRequest distributeTasksRequest) {
        List<Task> tasks = reportManager.getReportById(distributeTasksRequest.reportId()).getTasks();
        List<User> users = new ArrayList<>();

        List<UUID> userIds = distributeTasksRequest.userIds();
        for (UUID id : userIds) {
            User user = userManager.getUserById(id);
            users.add(user);
        }


        // Create a list of users with their current task counts
        List<UserTaskCount> userLoads = new ArrayList<>();
        for (User user : users) {
            userLoads.add(new UserTaskCount(user, user.getTasks().size()));
        }

        // Sort users by current task count (users with fewer tasks first)
        userLoads.sort(Comparator.comparingInt(utc -> utc.currentTaskCount));

        // Distribute new tasks to balance the overall load
        for (Task task : tasks) {
            // Always pick the user with the fewest total tasks
            UserTaskCount leastLoaded = userLoads.get(0);

            leastLoaded.user.getTasks().add(task);
            task.setUser(leastLoaded.user);

            userRepository.save(leastLoaded.user);

            leastLoaded.currentTaskCount++;

            // Re-sort to maintain order (more efficient than full sort each time)
            userLoads.sort(Comparator.comparingInt(utc -> utc.currentTaskCount));
        }
    }

    /**
     * Helper class to track user task counts during distribution
     */
    private static class UserTaskCount {
        User user;
        int currentTaskCount;

        UserTaskCount(User user, int currentTaskCount) {
            this.user = user;
            this.currentTaskCount = currentTaskCount;
        }
    }
}
