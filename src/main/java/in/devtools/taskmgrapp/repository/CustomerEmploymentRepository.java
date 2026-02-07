package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.CustomerEmployment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerEmploymentRepository  extends JpaRepository<CustomerEmployment, Long> {
    //
}
