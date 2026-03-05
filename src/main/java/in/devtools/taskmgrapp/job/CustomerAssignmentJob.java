package in.devtools.taskmgrapp.job;

import in.devtools.taskmgrapp.entity.User;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import in.devtools.taskmgrapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerAssignmentJob {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    // ─── Event ───────────────────────────────────────────────────────────────

    public record AssignChunkEvent(List<Long> customerIds) {}

    // ─── Async Listener ──────────────────────────────────────────────────────

    @Async("assignmentTaskExecutor")
    @EventListener
    @Transactional
    public void handleChunk(AssignChunkEvent event) {
        List<Long> ids = event.customerIds();

        log.info("[AssignmentJob] Processing chunk of {} customer(s).", ids.size());

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.warn("[AssignmentJob] No users found. Skipping chunk.");
            return;
        }

        // Shuffle the ID list for random distribution across users
        List<Long> shuffledIds = new java.util.ArrayList<>(ids);
        Collections.shuffle(shuffledIds);

        // Direct UPDATE — no SELECT, no entity loading, no N+1 on associations
        for (int i = 0; i < shuffledIds.size(); i++) {
            Long userId = users.get(i % users.size()).getId();
            customerRepository.assignUser(shuffledIds.get(i), userId);
        }

        log.info("[AssignmentJob] Chunk done — {} customer(s) assigned.", shuffledIds.size());
    }
}