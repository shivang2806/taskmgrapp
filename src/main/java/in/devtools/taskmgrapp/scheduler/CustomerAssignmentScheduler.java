package in.devtools.taskmgrapp.scheduler;

import in.devtools.taskmgrapp.entity.Customer;
import in.devtools.taskmgrapp.entity.User;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import in.devtools.taskmgrapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerAssignmentScheduler {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 600000) // every 10 minutes
    @Transactional
    public void assignUnassignedCustomers() {
        List<Customer> unassigned = customerRepository.findAllByAssignedUserIsNull();

        if (unassigned.isEmpty()) {
            log.info("[Scheduler] No unassigned customers found. Skipping.");
            return;
        }

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.warn("[Scheduler] No users available for assignment. Skipping.");
            return;
        }

        log.info("[Scheduler] Assigning {} customer(s) across {} user(s).", unassigned.size(), users.size());

        // Shuffle for randomness, then assign round-robin for equal distribution
        Collections.shuffle(unassigned);

        for (int i = 0; i < unassigned.size(); i++) {
            User assignedUser = users.get(i % users.size());
            unassigned.get(i).setAssignedUser(assignedUser);
        }

        customerRepository.saveAll(unassigned);

        log.info("[Scheduler] Assignment complete.");
    }
}