package in.devtools.taskmgrapp.scheduler;

import in.devtools.taskmgrapp.job.CustomerAssignmentJob;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerAssignmentScheduler {

    private static final int CHUNK_SIZE = 500;

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 600000, initialDelay = 10000)
    public void assignUnassignedCustomers() {

        // Fetch ALL unassigned IDs in one shot — avoids pagination shift bug
        // when async jobs start assigning while scheduler is still paginating
        List<Long> allUnassignedIds = customerRepository.findAllUnassignedIds();
        log.info("[Scheduler] DEBUG — unassigned count: {}", allUnassignedIds.size()); // add this

        if (allUnassignedIds.isEmpty()) {
            log.info("[Scheduler] No unassigned customers found. Skipping.");
            return;
        }

        log.info("[Scheduler] Found {} unassigned customer(s). Chunking into batches of {}.",
                allUnassignedIds.size(), CHUNK_SIZE);

        // Partition in memory and dispatch each chunk as an async job
        int totalChunks = 0;
        for (int i = 0; i < allUnassignedIds.size(); i += CHUNK_SIZE) {
            List<Long> chunk = allUnassignedIds.subList(i, Math.min(i + CHUNK_SIZE, allUnassignedIds.size()));
            eventPublisher.publishEvent(new CustomerAssignmentJob.AssignChunkEvent(chunk));
            totalChunks++;
            log.info("[Scheduler] Dispatched chunk {} — {} customer IDs queued.", totalChunks, chunk.size());
        }

        log.info("[Scheduler] Total {} customer(s) dispatched across {} chunk(s).",
                allUnassignedIds.size(), totalChunks);
    }
}