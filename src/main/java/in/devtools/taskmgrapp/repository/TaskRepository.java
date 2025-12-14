package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
